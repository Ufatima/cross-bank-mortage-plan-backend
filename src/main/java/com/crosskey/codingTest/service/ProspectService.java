package com.crosskey.codingTest.service;


import com.crosskey.codingTest.model.Prospect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProspectService {

    public ProspectService() {
    }

    /**
     * @param b
     * @param p
     * @return
     */
    static double pow(double b, int p) {
        if (p == 0) {
            return 1;
        }
        double result = b;
        for (int i = 1; i < p; i++) {
            result = b * result;
        }
        return result;
    }

    public Prospect processSingleProspect(Prospect prospect) {

        double result = calculate(prospect.getInterest(), prospect.getTotalLoan(), prospect.getYears());
        prospect.setResult(result);
        return prospect;

    }

    public List<Prospect> processProspect(MultipartFile file) {
        //TODO: return list of prospects

        String s = processFileUpload(file);
        List<Prospect> prospects = new ArrayList<>();
        if (s != null) {
            double yearlyInterest = 0;
            double totalLoan = 0;
            int years = 0;
            double E = 0;
            try {
                FileInputStream fstream = new FileInputStream(s);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                br.readLine();
                while ((strLine = br.readLine()) != null) {
                    String name;
                    if (!strLine.isEmpty() && strLine.charAt(0) == '\"') {
                        int lastIndexOfQuote = strLine.lastIndexOf('\"');
                        name = strLine.substring(0, lastIndexOfQuote);
                        String[] split = strLine.substring(lastIndexOfQuote + 2).split(",");
                        totalLoan = Double.parseDouble(split[0]);
                        yearlyInterest = Double.parseDouble(split[1]);
                        years = Integer.parseInt(split[2]);
                        E = calculate(yearlyInterest, totalLoan, years);
                        Prospect prospect = new Prospect(name, totalLoan, yearlyInterest, years);
                        prospect.setResult(E);
                        prospects.add(prospect);

                    } else if (strLine.length() > 5) {
                        String[] splitLine = strLine.split(",");
                        String customerName = splitLine[0].replace("\"", "\\\"");
                        totalLoan = Double.parseDouble(splitLine[1]);
                        yearlyInterest = Double.parseDouble(splitLine[2]);
                        years = Integer.parseInt(splitLine[3]);
                        E = calculate(yearlyInterest, totalLoan, years);
                        Prospect prospect = new Prospect(customerName, totalLoan, yearlyInterest, years);
                        prospect.setResult(E);
                        prospects.add(prospect);
                        System.out.println("Prospect :" + customerName + " wants to borrow " + totalLoan +
                                " € for a period of " + years + " years and pay " + E + " € each month");
                    }
                }
                fstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prospects;
    }

    private String processFileUpload(MultipartFile file) {
        String filePath = null;
        if (file.isEmpty()) {
            return filePath;
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            filePath = path.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * @param yearlyInterest
     * @param totalLoan
     * @param years
     * @return
     */
    public double calculate(double yearlyInterest, double totalLoan, int years) {
        double yearlyInterestRate;
        double monthlyInterest;
        int numberOfpayments;
        yearlyInterestRate = yearlyInterest / 100;
        monthlyInterest = (yearlyInterestRate) / 12;
        numberOfpayments = 12 * years;

        double v = 1 + monthlyInterest;
        double powResult = pow(v, numberOfpayments);

        double firstSeg = (monthlyInterest * powResult);
        double secSeg = (powResult - 1);

        double tempRes = firstSeg / secSeg;
        double res = totalLoan * tempRes;
        BigDecimal result = BigDecimal.valueOf(res);
        return result.setScale(2, RoundingMode.CEILING).doubleValue();
    }
}
