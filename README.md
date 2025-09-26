This project is an implementation of the Solitaire Cipher (also known as the Pontifex cipher), a card-based stream cipher designed by Bruce Schneier.
It uses a deck of playing cards to generate a keystream, which is then used to encrypt and decrypt alphabetic messages.
How It Works
1. A deck of cards (with two jokers) is arranged in a specific order. This arrangement is the secret key.
2. The deck is manipulated using the Solitaire algorithm:
  Move jokers, perform a triple cut, perform a count cut, and look up a card. Each iteration generates one number in the keystream.
3. To encrypt, plaintext letters (A–Z) are turned into numbers (0–25), then the keystream numbers are added (mod 26).
4. To decrypt, the same keystream is generated from the same starting deck, and numbers are subtracted (mod 26).


This is a symmetric cipher: both sender and receiver must know the same initial deck order.
