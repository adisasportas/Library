package library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberTest {
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("1", "Test Member");
    }

    @Test
    void testMemberCreation() {
        assertEquals("1", member.getId());
        assertEquals("Test Member", member.getName());
        assertTrue(member.getLoans().isEmpty());
    }

    @Test
    void testAddAndRemoveLoan() {
        Loan loan = new Loan(new Book("1", "Test Book", "Test Author", 2023), member, new Date(), new Date());
        member.addLoan(loan);
        assertEquals(1, member.getLoans().size());
        assertTrue(member.getLoans().contains(loan));

        member.removeLoan(loan);
        assertTrue(member.getLoans().isEmpty());
    }
}