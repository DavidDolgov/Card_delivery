package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    int addDays = 3;
    String date = generateDate(addDays, "dd");
    String date2 = generateDate(addDays, "dd.MM.yyyy");

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }


    @Test
    void happyPathTest1() {

        $("[data-test-id=city] .input__control").setValue("Пенза");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date2);
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id=notification] .notification__title").should(Condition.appear, Duration.ofSeconds(15));

        $("[data-test-id=notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date2));

    }

    @Test
    void happyPathTest2() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id=notification] .notification__title").should(Condition.appear, Duration.ofSeconds(15));

        $("[data-test-id=notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date2));

    }

    @Test
    void negativePathTestForInputFieldCityNotAnAdministrativeCenter() {

        $("[data-test-id=city] .input__control").setValue("Кузнецк");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityEnglishLetters() {

        $("[data-test-id=city] .input__control").setValue("Penza");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityNoData() {

        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForDataFieldDeliveryLessThanThreeDays() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    void negativePathTestForDataFieldWrongDate() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("34.45.6789");
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));

    }

    @Test
    void negativePathTestForDataFieldNOData() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));

    }

    @Test
    void negativePathTestForNameFieldEnglishLetters() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Dolgov David");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void negativePathTestForNameFieldNoData() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForPhoneFieldWrongNumber() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+7999888776655443322");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void negativePathTestForPhoneFieldLetters() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("qwerty");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void negativePathTestForPhoneFieldNoData() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForCheckbox() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        if (!LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(generateDate(addDays, "MM"))) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $x("//div//span[contains(text(),'Забронировать')]").click();

        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}
