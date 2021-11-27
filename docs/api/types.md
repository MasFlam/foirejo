# API Types

### User
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the user |
| username | String | The name of the user |

### Offer
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the offer |
| title | String | The title of the offer |
| shortDesc | String | The short description of the offer |
| ownerId | Long | The ID of the offer's owner |
| price | Long | The price, in atomic units of the currency |
| currency | String | The currency in which the offer's price is set |

### Trade
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the trade |
| offerId | Long | The ID of the offer this trade is about |
| price | Long | Price set at trade open, in piconero |
| buyerId | Long | The ID of the buyer in this trade (the seller is the offer owner) |

### TradeMessage
| Field | Type | Description |
| ----- | ---- | ----------- |
| date | DateTime | The date and time the message was sent |
| content | String | The content of the message |
