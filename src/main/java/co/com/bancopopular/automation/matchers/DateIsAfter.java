package co.com.bancopopular.automation.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DateIsAfter extends TypeSafeMatcher<Date> {
    private Date expectedDate;

    public DateIsAfter(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public static DateIsAfter than(Date expectedDate){
        return new DateIsAfter(expectedDate);
    }

    @Override
    protected boolean matchesSafely(Date actualDate) {
        if(actualDate != null){
            return actualDate.after(expectedDate) || actualDate.equals(expectedDate);
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" mayor o igual a \n");
        description.appendValue(this.expectedDate);
    }
}