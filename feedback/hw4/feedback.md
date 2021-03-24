### Answers Score: 17/26
- Pseudocode (Part 0): 5/8

Adding/subtracting a term to polynomial is undefined

- Altered Rep Invs (Parts 1a, 2b, and 2c): 4/6
In part 1a, the changes required are limited to the following methods:
`checkRep`, `toString`, `equals`, and `hashCode`

In part 2b, the changes required are limited to the following methods:
`checkRep`, `equals`, `toString`, `getExpt`, and `hashCode`.

In part 2c, the changes required are limited to the following methods: the
constructor, and `checkRep`.

- Mutability (Part 1b): 2/2

- checkRep Usage (Parts 1c, 2a, and 3a): 2/6
`RatNum` and `RatTerm` can get away with calling `checkRep` only in the
constructor because they both have immutability guaranteed by the compiler.
Notice how `RatTerm` is made up of only final `int` fields.  The compiler would
complain any time we try to mutate these fields in any way, so there is
theoretically no reason to include `checkRep` at the beginning and the end of
public methods, as long as the representation invariant is checked once at
initialization.  The story is similar with `RatTerm`.  You might argue that,
because it contains a final `RatNum`, immutability is not guaranteed by the
compiler if `RatNum` is accidentally mutable.  But, we just showed how its
immutability is guaranteed by the compiler, so we can use that in our reasoning
of whether immutability of `RatTerm` is guaranteed by the compiler.

Even though many observer methods are so simple that they can be implemented
with one line, this still does not mean calling `checkRep` in these methods is
useless.  The `checkRep` call at the top can catch violations to the
representation invariant that occured while the program was executing outside of
the context of the ADT (this would be an indication of representation exposure),
and it doesn't hurt to have the `checkRep` call at the bottom - it is still
theoretically possible that the body of the method accidentally mutated the ADT
somehow.

- RatPoly Design (Part 3b): 4/4

### Code Quality Score: 3/3

### Mechanics: 3/3

#### Overall Feedback

Remove all To-Do comments after finish implementing. You do not need to explain every single line of your code. Only add comments
on code that has complicated login. In `Ratpoly`, the write-up only ask you to write the loop invariants but not the pseudocode.

#### More Details

None.
