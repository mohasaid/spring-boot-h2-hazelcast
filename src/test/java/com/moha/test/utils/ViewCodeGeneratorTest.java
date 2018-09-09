package com.moha.test.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ViewCodeGeneratorTest {

    @Test
    public void viewCodeNotNullAndDoesNotContainDashes() {
        String viewCode = ViewCodeGenerator.getViewCode();
        assertNotNull(viewCode);
        assertFalse(viewCode.contains("-"));
    }

    @Test
    public void viewCodeNotRepeated() {
        String viewCode = ViewCodeGenerator.getViewCode();
        String otherViewCode = ViewCodeGenerator.getViewCode();
        assertNotNull(viewCode);
        assertNotNull(otherViewCode);
        assertFalse(viewCode.contains("-"));
        assertFalse(otherViewCode.contains("-"));
        assertNotEquals(viewCode, otherViewCode);
    }
}