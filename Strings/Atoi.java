class Solution {
    public int myAtoi(String s) {
        int n = s.length();
        int sign = 1;
        int result = 0;
        int i = 0;

        while(i<n && s.charAt(i)==' '){
            i++;
        }

        if(i<n && (s.charAt(i)=='-'||s.charAt(i)=='+')){
            sign = (s.charAt(i)=='-') ? -1 : 1;
            i++;
        }

        while(i<n && Character.isDigit(s.charAt(i))){
            int digit = s.charAt(i)-'0';
            if((result>Integer.MAX_VALUE/10) || (result==Integer.MAX_VALUE/10 && digit>Integer.MAX_VALUE%10)){
                return (sign==-1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            result = result*10+digit;
            i++;
        }
        return result*sign;
    }
}


// DFA

class Solution {
    public int myAtoi(String s) {
        //DFA Appraoch
        int state = 0;
        int n = s.length();
        int value = 0;
        int sign = 1;
        for(int i=0;i<n;i++){
            switch (state) {
                // white space case
                case 0 -> {
                    if(s.charAt(i)==' '){
                        continue;
                    } else if(Character.isDigit(s.charAt(i))){
                        value += (s.charAt(i)-'0');
                        state = 1;
                    } else if(s.charAt(i)=='+' || s.charAt(i)=='-'){
                        sign = (s.charAt(i)=='-') ? -1 : 1;
                        state = 2;
                    } else {
                        return 0;
                    }
                }
                //digit case
                case 1 -> {
                    if(Character.isDigit(s.charAt(i))){
                        int digit = s.charAt(i)-'0';
                        if(value > Integer.MAX_VALUE/10 || (value==Integer.MAX_VALUE/10 && digit>7)){
                            return (sign==-1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                        }
                        value = value*10 + digit;
                    }else{
                        return value*sign;
                    }
                }

                case 2 -> {
                    if(Character.isDigit(s.charAt(i))){
                        value+= (s.charAt(i))-'0';
                        state = 1;
                    }else{
                        return value;
                    }
                }
            }
        }
        return value*sign;
    }
}