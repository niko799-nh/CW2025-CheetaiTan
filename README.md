# Tetris Maintenance & Refactoring

Student: Tan Chee Tai

ID: 20593018

Mini Tetris is a modern, polished version of the classic Tetris game.
I focused on making the gameplay feel smooth, responsive, and fun to play while keeping the original mechanics familiar. The design is simple, clear, and easy to understand even for new players.

## GitHub Repository
**Repository Link:** https://github.com/niko799-nh/CW2025-CheetaiTan

---

## Compilation Instructions
To compile and run the application successfully, please follow these steps:

1. **Prerequisites:** Ensure **Java 21** (or a compatible JDK version) and **Maven** are installed on your system.
2. **Project Import:**
    - Open IntelliJ IDEA (or Eclipse).
    - Select File and navigate to the `COMP2042TanCheeTai` folder.
    - Ensure the project is imported as a **Maven Project** so that `pom.xml` dependencies (specifically JavaFX) load automatically.
3. **Build Configuration:**
    - If the Run Configuration is not automatically detected, set up a new Application configuration.
    - **VM Options:** If you are running on a modular JDK without Maven handling it, you may need: `--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml`.
4. **Execution:**
    - Run the project directly via the IDE "Run" button.
    - Alternatively, open the terminal in the project root and type: `mvn javafx:run`

---

## Implemented and Working Properly
The following features were successfully implemented and tested during the refactoring and extension phase:

* **Ghost Piece (Visual Guide):** I implemented a "shadow" block that appears at the bottom of the grid directly beneath the active falling piece. This required calculating the `dropDistance` dynamically every time the user moves the piece laterally, ensuring players can drop blocks with precision.
* **Next Bricks Preview:** The UI now renders a secondary grid showing the next three incoming tetrominoes. This involved creating a `BrickQueue` system that generates bricks in advance rather than generating them instantly at spawn.
* **Dynamic Leveling System:** The game now tracks lines cleared globally. For every 10 lines cleared, the level increases, and the `Timer` delay (falling speed) is reduced by 10%, creating a progressive difficulty curve.
* **Standardized Wall Kicks:** I improved the rotation logic. Previously, blocks near the wall would fail to rotate. I implemented a "Wall Kick" algorithm that checks if a rotation would result in an out-of-bounds error; if so, it temporarily shifts the block 1 unit away from the wall to allow the rotation to succeed.
* **Audio Feedback Loop:** A sound manager was added to trigger `.wav` files for specific events: `Move`, `Rotate`, `LineClear`, and `GameOver`. This runs on a separate thread to prevent UI freezing during playback.
* **Game State Management:** Implemented a Pause (`P`) and New Game (`N`) feature. The Pause feature halts the logic timer and renders a "PAUSED" overlay, preventing game logic from updating while the user is away.

---

## Implemented but Not Working Properly
Despite my best efforts, the following aspects have minor known issues:

* **Window Resizing Glitches:**
    * **Issue:** While the game window is technically resizable, resizing it during an active game session can sometimes cause the `GridPane` to misalign slightly, leaving small gaps between blocks until the next full refresh.
    * **Attempted Fix:** I tried binding the canvas width/height properties to the Stage scene, but JavaFX's refresh rate sometimes lags behind the manual resize event. I recommend playing in the default window size.
* **Game Over Sound Cut-off:**
    * **Issue:** When the game ends, the "Game Over" sound effect triggers but stops abruptly halfway through playback, failing to complete the full audio clip.
    * **Investigation:** I verified that the `.wav` file is not corrupted and has the correct duration. I attempted to play the sound on a separate, high-priority thread to ensure the stopping of the main game loop wouldn't affect it.
    * **Current Status:** The issue persists. I suspect the JavaFX MediaPlayer or AudioClip resource is being garbage-collected or forcibly stopped when the game transitions to the "Game Over" screen state, but I was unable to isolate the specific conflict in the code in time.
  
---

## Features Not Implemented
Due to strict time constraints and the prioritization of code refactoring, the following features were designed but not included:

1. **"Hold" Piece Mechanic:**
    * **Description:** The ability to "hold" a piece for later use (swapping the active piece).
    * **Reason for Omission:** This would have required a significant rewrite of the `GameController` logic to handle swapping states and preventing infinite stalling. I decided to prioritize the **Ghost Piece** and **Refactoring** as they added more immediate value to the visual gameplay.
2. **External Music Integration (Spotify/YouTube):**
    * **Description:** A feature allowing users to log in and select their own playlists from Spotify or YouTube to play as background music during gameplay.
    * **Reason for Omission:** Integrating third-party APIs requires complex authentication (OAuth 2.0) and dependency on an active internet connection. I decided to prioritize a self-contained, offline experience using local `.wav` sound effects to ensure stability and reduce the project's complexity.
3. **Configurable Keybindings:**
    * **Description:** A settings menu allowing users to remap keys (e.g., WASD vs Arrow Keys).
    * **Reason for Omission:** Creating a Settings UI scene and passing those preferences into the `InputHandler` was considered "nice to have" but not essential for the Minimum Viable Product.

---

## New Java Classes
I introduced several new classes to strictly adhere to the **Single Responsibility Principle (SRP)**:

* **LevelTracker.java:**
    * **Location:** `com.comp2042.controller`
    * **Purpose:** Manages the game's difficulty progression. It tracks cleared lines and calculates when to increase the level and speed, separating this logic from the main game loop.
* **SoundEffect.java:**
    * **Location:** `com.comp2042.audio`
    * **Purpose:** A dedicated class to handle loading audio resources and playing clips (Background music, Move, Clear Lines). This prevents the Main Controller from being cluttered with `AudioClip` logic.
* **GameInputProcessor.java:**
    * **Location:** `com.comp2042.controller`
    * **Purpose:** Handles all keyboard events (Arrow keys, Pause, New Game). By separating input listening from the game logic, the code becomes easier to read and allows for smoother control responsiveness.
---

## Modified Java Classes
I refactored the core architecture to follow the **Model-View-Controller (MVC)** pattern, ensuring strict separation of concerns:

* **SimpleBoard.java :**
    * **Changes:** This class now strictly manages the **Game State** (the grid, the active brick, and the score). I removed all drawing logic from it and added helper methods like getGhostPosition() and getNextThreeBrickShapes().
    * **Why:** To protect data integrity. By encapsulating the grid data and logic (like "Wall Kicks" and collision detection) inside the Model, the GUI cannot accidentally corrupt the game state.
* **GuiController.java :**
    * **Changes:** I centralized all visual updates here. It handles the JavaFX UI, draws the "Ghost Piece," and manages the "Next Bricks" panel. I also delegated the actual drawing to a GameRenderer helper to keep this class clean.
    * **Why:** The original code mixed logic and drawing. Now, this class focuses solely on **Presentation**—displaying what the user sees (Score, Level, Bricks) without knowing the complex rules of Tetris.
* **GameController.java :**
    * **Changes:** This class now acts as the pure **Bridge** between the Model (SimpleBoard) and the View (GuiController). It listens for events (like onDownEvent) and coordinates the game flow (e.g., "If the board says a line is cleared, tell the UI to play a sound").
    * **Why:** To decouple the system. The Input logic doesn't need to know how the Board works, and the Board doesn't need to know how the UI looks. This makes the code modular and easy to test.
  
---

## Unexpected Problems & Solutions
During the development process, I encountered several technical challenges:

1. **JavaFX "Not on FX Application Thread" Error:**
    * **Problem:** When I tried to update the "Level" label from inside the game loop `Timer`, the application crashed. This is because JavaFX prohibits background threads from touching the UI.
    * **Solution:** I wrapped the UI update code inside a `Platform.runLater()` block, pushing the update to the main event queue safely.
2. **Ghost Piece Overlapping Active Block:**
    * **Problem:** Initially, the ghost piece was drawn *after* the active block, causing the semi-transparent gray color to wash out the color of the actual falling block.
    * **Solution:** I refactored the `paint()` method to enforce a strict "Painter's Algorithm"—drawing the background first, then the ghost piece, and finally the active player block on top.
3. **Hardcoded File Paths:**
    * **Problem:** The original code (and my early tests) used absolute paths for images/sounds, which would fail on the examiner's machine.
    * **Solution:** I switched to using `getClass().getResource("/path")` to load assets relative to the classpath, ensuring the game runs correctly on any computer.

## Conclusion

I extended the original game by improving both gameplay features and user experience.
The additional preview bricks, ghost piece, line & level tracking, and polished visuals provide a smoother and more enjoyable Tetris experience.
The codebase remains clean, modular, and fully testable.