# GPSavedCardView

## Overview
`GPSavedCardView` displays a previously saved payment card. It can be expanded to request the card's CVV before performing a payment.

## Usage
### XML
```xml
<com.terminal3.gpcoreui.components.GPSavedCardView
    android:id="@+id/savedCard"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### Programmatic
```java
GPSavedCardView cardView = findViewById(R.id.savedCard);
cardView.setCardName("Mastercard Debit");
cardView.setMaskedCardNumber(".... 8217");
cardView.setCardBrandIcon(ContextCompat.getDrawable(this, R.drawable.ic_card_brand_master));
cardView.setState(SavedCardState.SELECTED);
```

## Attributes
GPSavedCardView does not define custom XML attributes.

## Methods
| Method | Description |
|-------|-------------|
| `setState(SavedCardState state)` | Change view state between DEFAULT and SELECTED |
| `getState()` | Retrieve current state |
| `toggle()` | Convenience method to switch state |
| `setCardBrandIcon(Drawable drawable)` | Set card brand icon |
| `setCardName(CharSequence name)` | Set card name label |
| `setMaskedCardNumber(CharSequence number)` | Set masked card number text |
| `getCvvField()` | Access the internal `GPCardCVVField` |
| `setOnMenuClickListener(View.OnClickListener)` | Listener for menu icon clicks |

## Examples
See `GPSavedCardFragment` in the sample app for a working example.

## Styling
The component uses rounded corners and a subtle border. When selected, the background is tinted with `gp_bg_selected_card`.
