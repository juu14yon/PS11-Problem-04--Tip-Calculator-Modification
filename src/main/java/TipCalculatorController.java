import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class TipCalculatorController {
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent = NumberFormat.getPercentInstance();
    private BigDecimal tipPercentage = new BigDecimal(0.15);

    @FXML private Label tipPercentageLabel;
    @FXML private TextField amountTextField;
    @FXML private TextField tipTextField;
    @FXML private TextField totalTextField;
    @FXML private Slider tipPercentageSlider;

    public void initialize(){
        currency.setRoundingMode(RoundingMode.HALF_UP);

        tipPercentageLabel.textProperty().bind(tipPercentageSlider.valueProperty().asString("%.0f%%"));

        tipPercentageSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        tipPercentage = BigDecimal.valueOf(newValue.intValue()/100.0);
                        try {
                            BigDecimal amount = new BigDecimal(amountTextField.getText());
                            BigDecimal tip = amount.multiply(tipPercentage);
                            BigDecimal total = amount.add(tip);

                            tipTextField.setText(currency.format(tip));
                            totalTextField.setText(currency.format(total));
                        }
                        catch (NumberFormatException ex) {
                            amountTextField.setText("Enter amount");
                            amountTextField.selectAll();
                            amountTextField.requestFocus();
                        }
                    }
                }
        );

        amountTextField.textProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        tipPercentage = BigDecimal.valueOf((int) tipPercentageSlider.getValue()/100.0);
                        try {
                            BigDecimal amount = new BigDecimal(amountTextField.getText());
                            BigDecimal tip = amount.multiply(tipPercentage);
                            BigDecimal total = amount.add(tip);

                            tipTextField.setText(currency.format(tip));
                            totalTextField.setText(currency.format(total));
                        }
                        catch (NumberFormatException ex) {
                            amountTextField.setText("Enter amount");
                            amountTextField.selectAll();
                            amountTextField.requestFocus();
                        }
                    }
                }
        );{

        }
    }

}
