class Solution {
    public List<String> addOperators(String num, int target) {
        /* we have to insert operators in between the integer values to get the value 
        target to do that. we need to traverse and check all the possiblities.
        so to check all the possibliltes we recursie and check if it is possible to get the value 
        if not we back track and find he next integer digit, here the integer digit is not single it can be 
        multiple size like two, three ones. 
        and for that we maintain two vaalues, currSum, path to track the path. once we reach the end of the string
        then we need to check if the currSum == target. if it is then we add that number ot the path.
        else we don't do it. 
        for addidition and subtraction this won't be a problem, but when it comes to the muitpliations.
        there will be a precendecne problem. so to handle that we add one thing called lastVal that will
        help in the caluateing the val when there is multiplicatoin operator.
        we also neeed to handle the leading zero case if the substring starts with zero then that integer 
        is invalid.
         */
         List<String> ans = new ArrayList<>();
         // (num,target,index,currSum,lastVal,path)
         generate(num,ans,target,0,0,0,"");
         return ans;
    }
    public void generate(String num,List<String> ans,int target,int index,long currSum,long lastVal,String path){
        if(index==num.length()){
            if(target==currSum){
                ans.add(path);
            }
            return;
        }

        for(int i=index;i<num.length();i++){
            long val = Long.parseLong(num.substring(index,i+1));

            if(index!=i  && num.charAt(index)=='0')break;

            //if the first index i is zero then we just add to the number.
            if(index==0){
                generate(num,ans,target,i+1,val,val,""+val);
            }else{
                // addition
                generate(num,ans,target,i+1,currSum+val,val,path+"+"+val);
                //sub
                generate(num,ans,target,i+1,currSum-val,-val,path+"-"+val);
                //multiplication
                generate(num,ans,target,i+1,currSum-lastVal+(lastVal*val),lastVal*val,path+"*"+val);
            }
        }
    }

}