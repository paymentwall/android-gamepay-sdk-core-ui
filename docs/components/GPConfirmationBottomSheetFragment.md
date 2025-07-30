# GPConfirmationBottomSheetFragment

## 1. Introduction
`GPConfirmationBottomSheetFragment` displays a modal bottom sheet to confirm a critical action such as removing a payment method.

## 2. Params definition
This component exposes no custom XML attributes. The confirmation message can be specified programmatically.

## 3. How to use in Activity / Fragment
```java
GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
sheet.setMessage("Remove Mastercard Credit .... 8217?");
sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
    @Override public void onRemove() { /* handle removal */ }
    @Override public void onCancel() { /* optional */ }
});
sheet.show(getSupportFragmentManager(), "confirm");
```

## 4. Interaction
- `setMessage(String)` – set the confirmation text
- `setOnDecisionListener(OnDecisionListener)` – listener for remove/cancel events
