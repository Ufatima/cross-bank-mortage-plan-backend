package com.crosskey.codingTest;

import com.crosskey.codingTest.model.Prospect;
import com.crosskey.codingTest.service.ProspectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class CodingTestApplicationTests {

	@Autowired
	ProspectService prospectService;

	@Test
	void calculateTest() {
		Prospect prospect = new Prospect("Juha", 1000, 5, 2);
		double calculate = prospectService.calculate(prospect.getInterest(), prospect.getTotalLoan(), prospect.getYears());
		Assertions.assertEquals(43.88, calculate);
	}

}
