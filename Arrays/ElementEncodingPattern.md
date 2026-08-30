This trick is commonly known as **element encoding using arithmetic** (or sometimes referred to as **frequency/value encoding using modulo and division**, or **storing two states in a single variable**).

In competitive programming and algorithm interviews, it falls under the broader category of **in-place space optimization techniques**.

### How the Math Works:

It relies on the fundamental division theorem: any integer $A$ can be uniquely represented as:


$$A = (Q \times M) + R$$


Where:

* **$R$ (Remainder)** is the original value ($A \pmod M$).
* **$Q$ (Quotient)** is the new value we want to store ($A / M$).
* **$M$ (Modulus)** is a number chosen to be strictly greater than any possible original value in the array.

This same trick can be used in other classic problems, such as finding the missing and repeating numbers in an array or modifying arrays in-place where you would otherwise need $O(N)$ extra space.

