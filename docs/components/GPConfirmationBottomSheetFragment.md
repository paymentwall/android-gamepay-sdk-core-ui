# GPConfirmationBottomSheetFragment

## 1. Introduction
`GPConfirmationBottomSheetFragment` displays a modal bottom sheet to confirm a critical action such as removing a payment method.

## 2. Params definition
This component exposes no custom XML attributes. The confirmation message can be specified programmatically.

## 3. How to use in Activity / Fragment

### Basic Usage
```java
GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
sheet.setTitle("Confirm Action");
sheet.setMessage("Are you sure you want to proceed?");
sheet.setShowDestructiveButton(true, "Remove");
sheet.setShowCancelButton(true, "Cancel");
sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
    @Override public void onPositiveClick() { /* handle positive action */ }
    @Override public void onDestructiveClick() { /* handle destructive action */ }
    @Override public void onCancel() { /* handle cancel */ }
});
sheet.show(getSupportFragmentManager(), "confirm");
```

### Card Removal Confirmation (Simplified)
```java
GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
sheet.setupCardRemovalConfirmation("Mastercard", "Credit", "8217");
sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
    @Override public void onPositiveClick() { /* not used */ }
    @Override public void onDestructiveClick() { /* handle card removal */ }
    @Override public void onCancel() { /* handle cancel */ }
});
sheet.show(getSupportFragmentManager(), "card_removal");
```

## 4. Interaction
- `setTitle(String)` – set the title text
- `setMessage(String)` – set the confirmation text
- `setShowPositiveButton(boolean, String)` – configure positive button visibility and text
- `setShowDestructiveButton(boolean, String)` – configure destructive button visibility and text
- `setShowCancelButton(boolean, String)` – configure cancel button visibility and text
- `setupCardRemovalConfirmation(String, String, String)` – quick setup for card removal with title "Confirm card removal" and subtitle with bold card details
- `setOnDecisionListener(OnDecisionListener)` – listener for button events
