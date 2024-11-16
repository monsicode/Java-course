package bg.sofia.uni.fmi.mjt.exchange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrencyExchangeTest {

    private final CurrencyConverter currencyConverterMock = mock();
    private final CurrencyExchange exchange = new CurrencyExchange(currencyConverterMock);

    @Test
    void testExchangeSumBetweenUSDtoBg() throws UnknownCurrencyException {
        when(currencyConverterMock.getExchangeRate(Currency.USD, Currency.BGN)).thenReturn(2.0);

        assertEquals(20.0, exchange.exchangeSum(Currency.USD, Currency.BGN, 10), 0.001,
            "Currency exchange should work properly!");

        verify(currencyConverterMock).getExchangeRate(Currency.USD, Currency.BGN);
        verify(currencyConverterMock, times(1)).getExchangeRate(Currency.USD, Currency.BGN);

    }


}
