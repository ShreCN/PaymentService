package com.example.paymentservice.paymentgateway;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class StripePaymentGateway implements PaymentGateway {
    @Value("${STRIPE_KEY_SECRET}")
    private String stripeSecretKey;

    @Override
    public String generatePaymentLink(Long orderId, Long amount) throws StripeException {
        //Call the Stripe API to generate the payment link.
        Stripe.apiKey = stripeSecretKey;

        PriceCreateParams priceParams =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(amount) // min 900000/- for business
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();


        Price price = Price.create(priceParams);

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(2L)
                                        .build()
                        ).setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://www.jiocinema.com/")
                                                        .build()
                                        )
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(params);

        return paymentLink.toString();
    }
}
