import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class TestDelivery {

    public String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    public void shouldTestV1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Иванов Иван Сергеевич");
        $("[data-test-id=phone] input").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        SelenideElement button = $x("//*[contains(text(), 'Забронировать')]");
        button.click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

}


