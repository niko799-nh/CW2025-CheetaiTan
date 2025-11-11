Mini Tetris is a modern, polished version of the classic Tetris game.
I focused on making the gameplay feel smooth, responsive, and fun to play while keeping the original mechanics familiar. The design is simple, clear, and easy to understand even for new players.

How to Run

1. Open the project in IntelliJ.
2. Make sure your Java version is compatible (Java 21 recommended).
3. Build and run using Maven: javafx:run 
 
Controls

- Move Left: ← or A
- Move Right: → or D
- Rotate: ↑ or W
- Soft Drop (move down faster): ↓ or S
- Hard Drop (instantly drop): Enter
- Pause / Resume: P
- New Game: N

Scoring & Levels

- Moving a piece down manually gives a small score increase.
- Clearing lines gives bonus points.
- After clearing enough lines, the level increases.
- Each level makes the falling speed faster (so the game gets more difficult).

What I Worked On

- My main focus was improving gameplay feedback and clarity:
- Added visual guidance (ghost piece, next bricks preview).
- Added dynamic speed changes based on lines cleared.
- Improved UI layout so score, lines, and level are displayed consistently.
- Added sound effects to make the game feel more alive.
- Cleaned up row-clearing logic so it updates smoothly.

Features I Added / Improved

- Ghost piece (shows where the brick will land)
- Display of the next three upcoming bricks
- Added wall kick logic so that pieces can rotate correctly near the left/right edges.
- Level progression and increasing falling speed
- Sounds for movement, rotation, line clear and game over
- Live score display
- Line clear counter
- “New Game” shortcut (press N)
- Pause and resume (press P)
- Game Over screen with final score shown clearly

