package com.dawit.oibsip_task3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText subOutput;
    private EditText activeInput;
    private boolean isTopOperator = false;
    private StringBuilder activeBuffer;
    private Stack<String> buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buffer = new Stack<>();
        this.buffer.push("0");
        this.subOutput = (EditText) findViewById(R.id.sub_output);
        this.activeInput = (EditText) findViewById(R.id.active_input);
        this.activeBuffer = new StringBuilder();

        subOutput.setVisibility(View.INVISIBLE);
    }

    public void onNumberClick(View view){
        Eval eval = new Eval();
        Button button = (Button) view;

        if (buffer.peek().toString().equals("0") && !button.getText().equals("0")){
            buffer.pop();
            buffer.push(button.getText().toString());
            this.activeInput.setText(buffer.peek());
        }else if (buffer.peek().toString().equals("0") && button.getText().equals("0")) {

        }else if (Eval.isNumeric(buffer.peek())) {
            String tempNum = buffer.pop();
            buffer.push(tempNum + button.getText().toString());
            this.activeInput.append(button.getText().toString());
            this.isTopOperator = false;
        }else {
            buffer.push(button.getText().toString());
            this.activeInput.append(button.getText().toString());
            this.isTopOperator = false;
        }

        int textLength = this.activeInput.getText().length();
        activeInput.setSelection(textLength, textLength);
        Stack<String> buff = (Stack<String>) buffer.clone();
        subOutput.setText(eval.evaluate(buff).toString().replace('[', ' ').replace(']', ' '));
        subOutput.setVisibility(View.VISIBLE);
    }

    public void onPtClick(View view){
        Button button = (Button) view;

        if (this.buffer.peek().equals("(")){
            return;
        }

        if (!buffer.peek().toString().contains(".")){
            String screenAppend = (this.isTopOperator) ? "0" + button.getText().toString() : button.getText().toString();
            String bufferAppend = (this.isTopOperator) ? "0" + button.getText().toString() : this.buffer.pop() + button.getText().toString();
            this.buffer.push(bufferAppend);
            this.activeInput.append(screenAppend);
        }
        int textLength = this.activeInput.getText().length();
        activeInput.setSelection(textLength, textLength);
    }

    public void onOpClick(View view){
        Button button = (Button) view;

        if (!this.isTopOperator && !buffer.peek().equals(".")){
            String appendTxt = button.getText().toString();

            if (button.getText().toString().equals("xy")) {
                appendTxt = "^";
                this.buffer.push(appendTxt);
            }else if (button.getText().toString().equals("√")) {
                appendTxt = "×" + button.getText().toString();
                this.buffer.push("×");
                this.buffer.push(button.getText().toString());
            }else {
                this.buffer.push(appendTxt);
            }

            this.activeInput.append(appendTxt);
            this.isTopOperator = true;
        }

        int textLength = this.activeInput.getText().length();
        activeInput.setSelection(textLength, textLength);
    }

    public void onParenthesis(View view) {
        Button button = (Button) view;

        if (this.buffer.peek().equals("(") || this.buffer.peek().equals(".") || this.buffer.peek().equals("0")){
            return;
        }

        if (Eval.isNumeric(this.buffer.peek()) || this.buffer.peek().equals(")")){
            this.buffer.push("×" + "(");
            this.activeInput.append("×" + "(");
            return;
        }

        if (Eval.isParenthesisOpen(this.buffer.toString())) {
            this.buffer.push(")");
            this.activeInput.append(")");
        }else {
            this.buffer.push("(");
            this.activeInput.append("(");
        }
    }

    public void onClearClick(View view){
        if (!buffer.isEmpty() && !activeInput.getText().toString().isEmpty()) {
            buffer.pop();
            if (buffer.isEmpty())
            {
                buffer.push("0");
                activeInput.setText("0");
            }else {
                StringBuilder sb = new StringBuilder(activeInput.getText().toString().trim());
                sb.setCharAt(activeInput.getText().toString().trim().length() - 1, ' ');
                activeInput.setText(sb.toString().trim());
            }

        }
    }

    public void onLongClearClick(View view){

    }

    public void onEqClick(View view){
        Stack<String> buff = (Stack<String>) buffer.clone();
        Eval eval = new Eval();

        subOutput.setText("0");
        subOutput.setVisibility(View.INVISIBLE);
        activeInput.setText(eval.evaluate(buff).toString().replace('[', ' ').replace(']', ' ').trim());
    }

}