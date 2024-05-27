# AIChat Android Application

AIChat is an Android application that leverages the Gemini Pro API to provide intelligent conversational capabilities similar to ChatGPT. Follow the steps below to set up and run the application.

## Prerequisites

- Android Studio installed on your machine.
- A valid Gemini API key.

## Installation and Setup

1. **Install Android Studio**
   - Download and install Android Studio from the [official website](https://developer.android.com/studio).
   - Follow the installation instructions provided on the website.

2. **Clone the Repository**
   - Clone this repository to your local machine using Git:
     ```bash
     git clone https://github.com/yourusername/AIChat.git
     cd AIChat
     ```

3. **Set GEMINI_API_KEY in local.properties**
   - Open the `local.properties` file located in the root directory of the project.
   - Add your Gemini API key to the file:
     ```properties
     GEMINI_API_KEY=<Your Gemini API Key>
     ```

4. **Run Android Studio**
   - Open Android Studio.
   - Click on `File` > `Open...` and select the cloned project directory.
   - Sync the project with Gradle files.
   - Once the project is synced, click on `Run` to build and run the application on an emulator or connected device.

## Usage

- Launch the AIChat application on your Android device.
- Start chatting with the AI, powered by Gemini Pro API.
