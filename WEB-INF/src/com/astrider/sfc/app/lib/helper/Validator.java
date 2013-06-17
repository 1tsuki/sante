package com.astrider.sfc.app.lib.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.astrider.sfc.app.lib.helper.FlashMessage.Type;
import com.astrider.sfc.app.lib.helper.annotation.Column;
import com.astrider.sfc.app.lib.helper.annotation.Valid;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

/**
 * 概要<br>
 *  BaseVoを継承するValueObject用バリデータ<br>
 *  @Validアノテーションの内容に応じてValidationを実行し、エラーメッセージを生成する<br>
 *<br>
 * 機能<br>
 *  主要機能<br>
 *      ・valid()            validationを実行。validか否かを返す。<br>
 *      ・getFlashMessage()  validationに失敗した項目があれば、エラーメッセージが格納されている<br>
 *<br>
 *  副次機能<br>
 *      ・isNotNull()<br>
 *      ・isNotBlank()<br>
 *      ・isLength()<br>
 *      ・isMaxLength()<br>
 *      ・isMinLength()<br>
 *      ・isMax()<br>
 *      ・isMin()<br>
 *      ・isRegex()<br>
 *      ・isPhone()<br>
 *      ・isEmail()<br>
 *      ・isUrl()<br>
 *
 * @author Itsuki Sakitsu
 *
 * @param <T>
 */
public class Validator<T extends BaseVo> {
    private FlashMessage flashMessage = new FlashMessage();
    private T vo;

    /**
     * @param vo
     */
    public Validator(T vo) {
        this.vo = vo;
        flashMessage.setMessageType(Type.WARNING);
    }

    public FlashMessage getFlashMessage() {
        return flashMessage;
    }

    /**
     * ValueObjectの全てのFieldに対してvalidationを実行
     * @return
     */
    public boolean valid() {
        boolean result = true;

        Field[] declaredFields = vo.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            result = validateField(f) && result;
        }
        return result;
    }

    /**
     * validation評価部分
     * validアノテーションが存在しなければ処理をスキップ
     * アノテーションが存在した場合、trueに設定されている全validationメソッドを実行する
     * @param f
     * @return
     */
    private boolean validateField(Field f) {
        boolean result = true;
        Valid v = f.getAnnotation(Valid.class);
        if (v == null) {
            return true;
        }

        for (Method vm : v.getClass().getDeclaredMethods()) {
            try {
                if (vm.getReturnType().equals(Boolean.TYPE) && !vm.getName().equals("equals")) {
                    if ((Boolean) vm.invoke(v)) {
                        Method method = this.getClass().getMethod(vm.getName(), Field.class, Valid.class);
                        result = (Boolean) method.invoke(this, f, v) && result;
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean isNotNull(Field f) {
        boolean valid = false;
        try {
            valid = f.get(vo) != null;
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }
        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "が未入力です";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }

    public boolean isNotNull(Field f, Valid v) {
        return isNotNull(f);
    }

    public boolean isNotBlank(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                String value = String.valueOf(f.get(vo));
                valid = StringUtils.isNotEmpty(value);
            }
        } catch (ClassCastException e) {
            valid = false;
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "が未入力です";
            flashMessage.addMessage(logicalName + errorMessage);
        }

        return valid;
    }

    public boolean isLength(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                String value = String.valueOf(f.get(vo));
                valid = v.length() == value.length();
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の長さは" + v.length() + "文字にする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }

        return valid;
    }

    public boolean isMinLength(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                String value = String.valueOf(f.get(vo));
                valid =  v.minLength() <= value.length();
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の長さは" + v.minLength() + "文字以上にする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }

    public boolean isMaxLength(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                String value = String.valueOf(f.get(vo));
                valid = value.length() <= v.maxLength();
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の長さは" + v.maxLength() + "文字以下にする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }

    public boolean isMin(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                int value = (Integer) f.get(vo);
                valid = v.min() <= value;
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の値は" + v.min() + "以上にする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }

    public boolean isMax(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                int value = (Integer) f.get(vo);
                valid = value <= v.max();
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の値は" + v.max() + "以下にする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }

    public boolean isRegexp(Field f, Valid v) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            } else {
                valid = checkRegexp(f, v.regexp());
            }
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }
        return valid;
    }

    public boolean isUrl(Field f, Valid v) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return checkRegexp(f, regex);
    }

    public boolean isEmail(Field f, Valid v) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return checkRegexp(f, regex);
    }

    public boolean isPhone(Field f, Valid v) {
        String regex = "^\\d{1,4}?-\\d{1,4}?-\\d{1,4}$";
        return checkRegexp(f, regex);
    }

    private boolean checkRegexp(Field f, String regex) {
        boolean valid = false;
        try {
            if (f.get(vo) == null) {
                valid = true;
            }
            String value = (String) f.get(vo);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            valid =  matcher.matches();
        } catch (IllegalArgumentException e) {
            valid = false;
        } catch (IllegalAccessException e) {
            valid = false;
        }

        if (!valid) {
            Column c = f.getAnnotation(Column.class);
            String logicalName = c.logic();
            String errorMessage = "の値は指定のフォーマットにする必要があります";
            flashMessage.addMessage(logicalName + errorMessage);
        }
        return valid;
    }
}
