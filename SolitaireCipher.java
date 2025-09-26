public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher (Deck key) {
	this.key = new Deck(key); // deep copy of the deck
    }

    /* 
     * TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {
	/**** ADD CODE HERE ****/
        int keyStream[] = new int[size];
		int eachKey;
		for(int i = 0; i < size; i++) {
			eachKey = key.generateNextKeystreamValue();
			while (eachKey == 0) {
				eachKey = key.generateNextKeystreamValue();
			}
			keyStream[i] = eachKey;
		}
		return keyStream;
    }

    /* 
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
	/**** ADD CODE HERE ****/
        String cipherString = "";
		char cipherChar;
		int cipherValue;
	    msg = msg.replaceAll("[^A-Za-z]", "").toUpperCase();
		int[] keystream = getKeystream(msg.length());
		for(int i = 0; i < msg.length(); i++) {
			cipherValue = (msg.charAt(i) + keystream[i] - 'A') % 26;
			cipherChar = (char) ('A' + cipherValue);
			cipherString += cipherChar;
		}
		return cipherString;
    }

    /* 
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
	/**** ADD CODE HERE ****/
		String cipherString = "";
		char cipherChar;
		int cipherValue;
		
	    msg = msg.replaceAll("[^A-Za-z]", "").toUpperCase();
		int[] keystream = getKeystream(msg.length());
		for(int i = 0; i < msg.length(); i++) {
			cipherValue = ('Z' - (msg.charAt(i) - keystream[i])) % 26;
			cipherValue = cipherValue % 26;
			cipherChar = (char) ('Z' - cipherValue);
			cipherString += cipherChar;
		}
		return cipherString;
    }

}
