# AGENT instructions for android-gamepay-sdk-core-ui repository

These instructions define the development guidelines and tasks for the Android GamePay SDK Core UI project. The project is written entirely in Java and avoids any Material Design dependencies. All custom components, themes, and utilities must be implemented from scratch.

## Project Structure
- Android library module: **gp-core-ui**
- Sample application module: **sample-app**
- Minimum SDK: API 21
- Only use `AppCompat` for basic compatibility
- All classes must be prefixed with `GP`
- Base package for the library: `com.terminal3.gamepayui`

## Directory Layout
```
com/terminal3/gamepayui/
    theme/
    components/
    utils/
    models/
    enums/
```

## Key Development Tasks
1. **Theme System**
   - Define colors, typography, and dimensions in XML resources.
   - Implement drawable selectors for input states and custom icons.
2. **Core UI Components**
   - Base input component (`BaseGPEditText`) with `InputState` enum and state management.
   - Input container (`GPInputContainer`) with label, error, helper text, and animations.
   - Specialized inputs: card number, CVV, expiry date, generic text, etc.
   - Payment form and custom button components.
3. **Utilities**
   - Validation utilities implementing Luhn algorithm, card type detection, and formatting helpers.
   - Animation utilities and theme manager for centralized styling.
4. **Sample Application**
   - Activities showcasing individual components, complete form demo, and testing playground.
5. **Testing**
   - JUnit tests for utilities and input management.
   - Robolectric and Espresso tests for Android behavior.
6. **Accessibility & Performance**
   - Provide content descriptions, focus management, and optimized drawing.
7. **Library Publishing**
   - Configure module for publishing with ProGuard rules and versioning.

## General Guidelines
- All UI must support input states: **DEFAULT**, **ACTIVE**, **ERROR**, **FILLED**, **INACTIVE**.
- Components should be lightweight, reusable, and customizable via XML attributes.
- Follow Java and Android best practices, with thorough documentation for all public APIs.
- No dependencies on Material Design libraries.
- Ensure compatibility across different Android versions and screen sizes.
