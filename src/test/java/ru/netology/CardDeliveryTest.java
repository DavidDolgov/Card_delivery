package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }


    @Test
    void happyPathTest1() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id=notification] .notification__title").should(Condition.appear, Duration.ofSeconds(15));

        SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
        String date2 = format2.format(c.getTime());
        String expected = "Встреча успешно забронирована на " + date2;
        String actual = $("[data-test-id=notification] .notification__content").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForInputFieldCityNotAnAdministrativeCenter() {

        $("[data-test-id=city] .input__control").setValue("Кузнецк");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Доставка в выбранный город недоступна";
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForInputFieldCityEnglishLetters() {

        $("[data-test-id=city] .input__control").setValue("Penza");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Доставка в выбранный город недоступна";
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForInputFieldCityNoData() {

        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForDataFieldDeliveryLessThanThreeDays() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        String date = format1.format(c.getTime());
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Заказ на выбранную дату невозможен";
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

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

        String expected = "Неверно введена дата";
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

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

        String expected = "Неверно введена дата";
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForNameFieldEnglishLetters() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Dolgov David");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForNameFieldNoData() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForPhoneFieldWrongNumber() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+7999888776655443322");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForPhoneFieldLetters() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("qwerty");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForPhoneFieldNoData() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForCheckbox() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $x("//div//span[contains(text(), 'Пенза')]").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format1 = new SimpleDateFormat("dd");
        String date = format1.format(c.getTime());
        if (date.equals("01")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("02")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        if (date.equals("03")) {
            $("[data-step='1'].calendar__arrow").click();
        }
        $x("//td[contains(text()," + date + ")]").click();
        $("[data-test-id=name] .input__control ").setValue("Долгов Давид");
        $("[data-test-id=phone] .input__control ").setValue("+79998887766");
        $x("//div//span[contains(text(),'Забронировать')]").click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных";
        String actual = $("[data-test-id=agreement].input_invalid .checkbox__text").getText().trim();
        Assertions.assertEquals(expected, actual);

    }

}
