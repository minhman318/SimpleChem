package com.manminh.simplechem.ui.balance;

import com.manminh.simplechem.balance.BalanceTool;
import com.manminh.simplechem.balance.engine.MathematicalBalanceEngine;
import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.balance.exception.ParseFormulaException;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.ui.base.BasePresenter;

public class BalancePresenter<V extends IBalanceView> extends BasePresenter<V> implements IBalancePresenter<V> {
    private BalanceTool mTool;

    public BalancePresenter() {
        mTool = new BalanceTool();
    }

    @Override
    public void balance(String equation) {
        mTool.balance(equation, new MathematicalBalanceEngine(), createHandler());
    }

    private BalanceTool.OnBalanceResultListener createHandler() {
        return new BalanceTool.OnBalanceResultListener() {
            @Override
            public void onBalanceSuccessful(Equation result) {
                getView().showResult(result.toHtmlSpanned());
            }

            @Override
            public void onHasBeenBalanced(Equation result) {
                getView().showInfo("Không cần! Phương trình này đã cân bằng.");
            }

            @Override
            public void onBalanceFailed() {
                getView().showError("Xin lỗi! Chúng tôi không thể cân bằng phương trình này. Vui lòng kiểm tra lại tính hợp lệ của phương trình.");
            }

            @Override
            public void onParseEquationFailed(int exCode) {
                String msg;
                switch (exCode) {
                    case ParseEquationException.INVALID_EQUATION:
                        msg = "Phương trình không hợp lệ. Vui lòng kiểm tra sự có mặt của các nguyên tố.";
                        break;
                    case ParseEquationException.INVALID_SYNTAX:
                        msg = "Cú pháp phương trình không hợp hệ. Cú pháp đúng là \"A + B -> C + D\"";
                        break;
                    default:
                        msg = "Lỗi không xác định.";
                        break;
                }
                getView().showError(msg);
            }

            @Override
            public void onParseFormulaFailed(int exCode) {
                String msg;
                switch (exCode) {
                    case ParseFormulaException.EMPTY_STRING:
                        msg = "Công thức không thể rỗng.";
                        break;
                    case ParseFormulaException.INVALID_CHARACTER:
                        msg = "Chứa kí tự không hợp lệ.";
                        break;
                    case ParseFormulaException.INVALID_ELEMENT:
                        msg = "Nguyên tố không hợp lệ hoặc chưa được hỗ trợ.";
                        break;
                    case ParseFormulaException.INVALID_PARENTHESES:
                        msg = "Có vẻ như bạn thiếu dấu \')\' đâu đó.";
                        break;
                    default:
                        msg = "Lỗi không xác định.";
                        break;
                }
                getView().showError(msg);
            }
        };
    }
}
