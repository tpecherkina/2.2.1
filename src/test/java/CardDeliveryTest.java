import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:7777/");
          
    }

    @Test
    void shouldTestCorrectFilling() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Татьяна");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + orderDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldTestWrongTown() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Марс");
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Таня");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldTestWrongDayFormat() {
        String orderDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys("09252013");
        $("[data-test-id=name] input").setValue("Таня");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldTestDateInThePast() {
        String orderDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys("20.06.2021");
        $("[data-test-id=name] input").setValue("Таня");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestWrongName() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Tanya");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWrongPhoneNumber() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Таня");
        $("[data-test-id=phone] input").setValue("+7919343777000");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestUnclickedAgreement() {
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79998889900");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestEmptyFilling() {
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
        
    @Test
        String orderDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Москва");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(orderDate);
        $("[data-test-id=name] input").setValue("Татьяна");
        $("[data-test-id=phone] input").setValue("+79193437770");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

}
}
