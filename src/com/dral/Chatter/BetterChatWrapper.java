package com.dral.Chatter;

public class BetterChatWrapper {
	
    private static final int[] characterWidths = new int[] {
        1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9,
        8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9,
        4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6,
        7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6,
        3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6,
        6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
        6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6,
        8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6,
        9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
        9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9,
        8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7,
        7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1
    };
    private static final char COLOR_CHAR = '\u00A7';
    private static final char SPACE_CHAR = '\u0020';
    private static final char OTHER_COLOR_CHAR = '\u0026';
    private static final int CHAT_WINDOW_WIDTH = 320;
    private static final int CHAT_STRING_LENGTH = 119;
    private static final String allowedChars = net.minecraft.server.FontAllowedCharacters.allowedCharacters;

    public static String[] wrapText(final String text) {
        final StringBuilder out = new StringBuilder();
        char colorChar = 'f';
        int lineWidth = 0;
        int lineLength = 0;
        int wordLength = 0;
        int wordWidth = 0;
        int lineIsGood = 0;
    	int lastSpacePostition = 0;

        // Go over the message char by char.
        for (int i = 0; i < text.length(); i++) {
        	//just setting the local variable(s)
            char ch = text.charAt(i);
            
            // Get the color, if it is a color the rest will be skipped.
            if ((ch == COLOR_CHAR || ch == OTHER_COLOR_CHAR) && i < text.length() - 1) {
                colorChar = text.charAt(++i);
                if (Character.toString(colorChar).matches("[0-9a-fA-F]")) {
                	out.append(COLOR_CHAR).append(colorChar);
                } else {
                	out.append("&").append(colorChar);
                }
                
                lineLength += 2;
                wordLength += 2;
                continue;
            }

            // Figure out if it's allowed, else skip it or do something :/
            int index = allowedChars.indexOf(ch);
            if (index == -1) continue; else index += 32;
            
            // Find the width
            final int width = characterWidths[index];
            
            //if the char is a space, do the great trick :D
    	    if (ch == SPACE_CHAR) {
    	    	//if the word is longer then allowed, go back to the place where it was allowed and add a break
    	    	if (wordLength > CHAT_STRING_LENGTH || wordWidth > CHAT_WINDOW_WIDTH) {
    	    		out.delete(lineIsGood, out.length()).append('\n');
    	    		i = lineIsGood;
    	    		lineLength = 0;
    	    		lineWidth = 0;
    			    wordLength = 0;
    			    wordWidth = 0;
    			    lineIsGood = 0;
    			    if (colorChar != 'f' && colorChar != 'F') {
    			    	out.append(COLOR_CHAR).append(colorChar);
    			    	lineLength += 2;
    	                wordLength += 2;
    			    }
    			    continue;
    	    	}
    	    	//if the line is longer, we just go back to the last space so it is not ugly :D
    	    	if (lineLength + 1 > CHAT_STRING_LENGTH || lineWidth + width >= CHAT_WINDOW_WIDTH) {
    		    	out.delete(lastSpacePostition, out.length()).append('\n');
    		    	i = lastSpacePostition;
    	    		lineLength = 0;
    	    		lineWidth = 0;
    			    wordLength = 0;
    			    wordWidth = 0;
    			    lineIsGood = 0;
    			    if (colorChar != 'f' && colorChar != 'F') {
    			    	out.append(COLOR_CHAR).append(colorChar);
    			    	lineLength += 2;
    	                wordLength += 2;
    			    }
    			    continue;
    		    }
    	    	wordLength = 0;
    	    	wordWidth = 0;
    	    	lastSpacePostition = i;
            }

    	    //if the line is not to long or to big, set the place to return to, to current.
    	    if (lineLength < CHAT_STRING_LENGTH && lineWidth < CHAT_WINDOW_WIDTH) 
    	    	lineIsGood = i;

    	    //add the current with + lenght to the global ones, and add the char to the string.
            out.append(ch);
            lineLength++;
            wordLength++;
            lineWidth += width;
            wordWidth += width;
        }

        // Return it split
        return out.toString().split("\n");
    }
}