package vpchc.prohealth;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class FAQsActivity extends AppCompatActivity {
    String categories[]={};
    String categoryQuestions[]={};
    String categoryAnswers[]={};

    private Spinner spinnerCategories;
    private int selectionCategory;

    Dialog faqsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFAQs);
        setSupportActionBar(toolbar);

        //Sets company logo text to custom font due to it being unavailable natively
        String fontPath = "fonts/franklinGothicMedium.ttf";
        TextView titleText = (TextView) findViewById(R.id.title_faqs);
        Typeface customTitleText = Typeface.createFromAsset(getAssets(), fontPath);
        titleText.setTypeface(customTitleText);

        //Back button listener
        View buttonBack = findViewById(R.id.faqsBackButton);
        buttonBack.setOnClickListener(faqsListener);

        categories = getResources().getStringArray(R.array.faqs_categories);

        spinnerCategories = (Spinner)findViewById(R.id.spinnerFAQsCategories);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.fancy_spinner_item, categories);
        adapterCategories.setDropDownViewResource(R.layout.fancy_spinner_dropdown);
        spinnerCategories.setAdapter(adapterCategories);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        selectionCategory = 0;
                        categoryQuestions = getResources().getStringArray(R.array.faqs_bill_questions);
                        categoryAnswers = getResources().getStringArray(R.array.faqs_bill_answers);
                        faqsPopup(1);
                        break;
                    case 2:
                        selectionCategory = 1;
                        categoryQuestions = getResources().getStringArray(R.array.faqs_misc_questions);
                        categoryAnswers = getResources().getStringArray(R.array.faqs_misc_answers);
                        faqsPopup(1);
                        break;
                    case 3:
                        selectionCategory = 2;
                        categoryQuestions = getResources().getStringArray(R.array.faqs_newpat_questions);
                        categoryAnswers = getResources().getStringArray(R.array.faqs_newpat_answers);
                        faqsPopup(1);
                        break;
                    case 4:
                        selectionCategory = 3;
                        categoryQuestions = getResources().getStringArray(R.array.faqs_services_questions);
                        categoryAnswers = getResources().getStringArray(R.array.faqs_services_answers);
                        faqsPopup(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private boolean faqsPopup(int choice){
    /*
	    Arguments:   choice(0 - dismiss dialog, 1 - create a dialog)
	    Description: Displays or dismisses a dialog with bh or medical providers listed.
	    Returns:     true
    */
        if(choice == 0) {
            faqsDialog.dismiss();
            spinnerCategories.setSelection(0);
            return true;
        }else{
            //This cond. statement is to make the styling of the dialog look more modern on devices
            //that support it which are API's >= 14
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                faqsDialog = new Dialog(FAQsActivity.this);
            }else{
                faqsDialog = new Dialog(FAQsActivity.this, R.style.AppTheme_NoActionBar);
            }
            faqsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            faqsDialog.setContentView(R.layout.dialog_faqs);
            faqsDialog.show();
            faqsDialog.setCancelable(false);
            faqsDialog.setCanceledOnTouchOutside(false);
        }
        //Dialog Close Button Listener
        View buttonDialogClose = faqsDialog.findViewById(R.id.buttonDialogCloseFAQs);
        buttonDialogClose.setOnClickListener(faqsListener);

        //Sets title
        TextView textDialogTitle = (TextView) faqsDialog.findViewById(R.id.faqsSectionTitle);
        textDialogTitle.setText(categories[selectionCategory + 1]);

        //Populates the Questions/Answers from the selected category
        LinearLayout faqsContent = (LinearLayout) faqsDialog.findViewById(R.id.faqsContent);
        for(int i = 0;i < (categoryQuestions.length);i++){
            TextView questionToAdd = new TextView(this);
            questionToAdd.setText(categoryQuestions[i]);
            questionToAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_content));
            questionToAdd.setTypeface(questionToAdd.getTypeface(), Typeface.BOLD);
            questionToAdd.setTextColor(Color.parseColor("#000000"));
            faqsContent.addView(questionToAdd);
            if(i != 0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)questionToAdd.getLayoutParams();
                params.setMargins(0, 30, 0, 0);
                questionToAdd.setLayoutParams(params);
            }
            TextView answerToAdd = new TextView(this);
            answerToAdd.setText(categoryAnswers[i]);
            answerToAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_content));
            faqsContent.addView(answerToAdd);
        }
        return true;
    }

    private View.OnClickListener faqsListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.faqsBackButton:
                    v.setSelected(true);
                    finish();
                    break;
                case R.id.buttonDialogCloseFAQs:
                    faqsPopup(0);
                    break;
                default:
                    break;
            }
        }
    };
    }
