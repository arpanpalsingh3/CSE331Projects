### Answers Score: 4/6

testBaseCase failed for the same reason that testThrowsIllegalArgumentException
did, so the fix is the same as identified in question 1.

testBaseCase failed for the same reason that testThrowsIllegalArgumentException
did, but for no other reason.  The fix identified for testBaseCase belongs as a
fix for testInductiveCase instead.

### Code quality score: 3/3

When selecting a greeting in RandomHello, the best style would use the length
of the array to specify the maximum value for the random integer generation:

String nextGreeting = greetings[rand.nextInt(greetings.length)];

Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

### Mechanics: 3/3

#### General Feedback

Nice clean code.

#### Specific Feedback
