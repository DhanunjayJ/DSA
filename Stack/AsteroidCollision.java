while (i < n) {
    int asteroid = asteroids[i];

    // Case 1: Asteroid moving right (+) or Stack is empty or Left-moving (-) asteroid hits another Left-moving one
    // No collision possible here.
    if (asteroid > 0 || ast.isEmpty() || ast.peek() < 0) {
        ast.push(asteroid);
        i++;
    } 
    // Case 2: Negative asteroid hits a positive asteroid on the stack
    else if (Math.abs(asteroid) > ast.peek()) {
        // Negative is bigger: destroy the positive one and STAY at index i to check the next one
        ast.pop();
    } 
    else if (Math.abs(asteroid) == ast.peek()) {
        // It's a tie: destroy both!
        ast.pop();
        i++; // This negative asteroid is now gone, move to next
    } 
    else {
        // Negative is smaller: it explodes, positive survives.
        // Just move to the next asteroid in the input array.
        i++;
    }
}