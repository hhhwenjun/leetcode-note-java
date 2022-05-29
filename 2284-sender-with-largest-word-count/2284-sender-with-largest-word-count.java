class Solution {
    public String largestWordCount(String[] messages, String[] senders) {
        // create a hash map to match the senders and word count
        Map<String, Integer> countMap = new HashMap<>();
        // both messages and senders should has same length
        int n = messages.length;
        for (int i = 0; i < n; i++){
            String sender = senders[i];
            String message = messages[i];
            // clean the messages and count its length
            String[] messageArry = message.split("\\s+");
            int stringCount = 0;
            for (String msg : messageArry){
                msg.trim();
                if (!msg.equals(" ")){
                    stringCount++;
                }
            }
            if (countMap.containsKey(sender)){
                countMap.put(sender, countMap.get(sender) + stringCount);
            }
            else {
                countMap.put(sender, stringCount);
            }
        }
        int maxLength = 0;
        int preMax = 0;
        String maxSender = "";
        // loop through the map to find max sender
        for (Map.Entry<String, Integer> entry : countMap.entrySet()){
            //maxLength = Math.max(maxLength, entry.getValue());
            
            if (entry.getValue() > maxLength){
                maxSender = entry.getKey();
                maxLength = entry.getValue();
            }
            else if (entry.getValue() == maxLength){
                if (entry.getKey().compareTo(maxSender) > 0){
                    maxSender = entry.getKey();
                }
            }
        }
        return maxSender;
    }
}