package com.test.quickyfox.Buyers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.quickyfox.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class TermsAndConditionActivity extends AppCompatActivity {

    String htmlCode = "<h6> As a condition of use, you abide to not use the Services for any purposes that are unlawful or prohibited by this term, or any other purpose not reasonably intended by the application. By way of example, and not as a limitation, you agree not to use the Services in any of the following:</h6>"
            +"<ul><li>To abuse, harass, harass, threaten, impersonate or intimidate any persons;</li></ul>"
            + "<ul><li>To use the Services if I am not 18 years of age or over and of legal age to view or purchase content and services from the Application</li></ul>"
            + "<ul><li>To post or transmit, or cause to be posted or transmitted, any Content that is libelous, defamatory, obscene, pornographic, abusive, offensive, profane, or that infringes any copyright or other rights to any person;</li></ul>"
            + "<ul><li>For any purpose (including posting or viewing Content) that is not permitted under laws of the jurisdiction where you use the Services;</li></ul>"
            + "<ul><li>To post or transmit or cause to be posted or transmitted, any communication or solicitation designed or intended to obtain password, account or private information form any Users,</li></ul>"
            + "<ul><li>To create or transmit unwanted `spam’ to any person or any URL</li></ul>"
            + "<ul><li>To create multiple accounts for the purpose of buying or selling certain products with the name or address of any other person</li></ul>"
            + "<ul><li>To post copyrighted Content which doesn’t belong to you, with exception of Blogs, where you may post such Content with explicit mention of the author’s name and link to the source of the Content;</li></ul>"
            + "<ul><li>With exception of accessing RSS feeds you will not use any robot, spider, scraper or other automated means to access the Site for any purpose without our express written permission. <br> Additionally, you agree that disproportionately large load on our infrastructure, <br>(ii) interfere or attempt to interfere with the proper working of the Application or any activities conducted on the Application; or <br>(iii) bypass any measures we may use to prevent or restrict access to the Application;</li></ul>"
            + "<ul><li>To artificially inflate or alter product counts;</li></ul>"
            + "<ul><li>To manipulate the price of any item or interfere with any other user's listings;</li></ul>"
            + "<ul><li>To post false, inaccurate, misleading, deceptive, defamatory, or libelous content;</li></ul>"
            + "<ul><li>To transfer your account (including Feedback) and user ID to another party without our consent;</li></ul>"
            + "<ul><li>To distribute or post spam, unsolicited or bulk electronic communications, chain letters, or pyramid schemes;</li></ul>"
            + "<ul><li>To distribute viruses or any other technologies that may harm the Application or the interests or property of users;</li></ul>"
            + "<ul><li>To harvest or otherwise collect information about users without their consent; or</li></ul>"
            + "<ul><li>To circumvent any technical measures used to provide the Services.</li></ul>"
            + "<h6>Listing Services Conditions:</h6>"
            + "<ul><li>Service Provider must meet Quicky Fox’s minimum performance standards. Failure to meet these standards may result in Quicky Fox’s charging you additional fees, and/or limiting, restricting, suspending, or downgrading your Service Provider Account.</li><ul>"
            + "<ul><li>You are responsible for the accuracy and content of the listing and services being offered,</li></ul>"
            + "<ul><li>Content that violates any of Quicky Fox's policies may be modified, obfuscated or deleted at Quicky Fox's discretion,</li></ul>"
            + "<ul><li>We may revise product data associated with listings to supplement, remove, or correct information</li></ul>"
            + "<h6>Availing of Service Conditions:</h6>"
            + "<ul><li>You are responsible for reading the full item listing before making a bid or commitment to avail the service,</li></ul>"
            + "<ul><li>You enter into a legally binding contract to purchase or avail an service when you commit to avail the service,</li></ul>"
            + "<ul><li>You may report to us if the Service Provider doesn't met the promised service that is being offered</li></ul>"
            + "<ul><li>You agree to pay all applicable, undisputed fees for the Services on the terms set forth on the invoice, any and all payments you make with the Seller’s products or services are final and non-refundable. If any products or services of the Service Provider is by defective or wrong it can be requested to be return and change the products or services for <br> the Buyers satisfaction. </li></ul>"
            + "<footer><br></footer>"
            ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        HtmlTextView textView = findViewById(R.id.html_terms_and_condition);
        textView.setHtml(htmlCode);

    }
}
