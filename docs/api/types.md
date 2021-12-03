# API Types

<!-- TODO: Partial types -->

### User
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the user |
| username | String | The name of the user |
| trust | Trust | The user's trust stats |

### Trust
| Field | Type | Description |
| ----- | ---- | ----------- |
| joinDate | DateTime | The date and time the user created their account |
| upCount | Int | Number of positive ratings of the user (see [`/api/user/{userId}/rate`](users.md#get-useridrate)) |
| downCount | Int | Number of negative ratings of the user |
| tradeCount | Int | The number of trades this user has completed |
| lostCount | Int | The number of trade disputes lost by this user |

### Offer
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the offer |
| title | String | The title of the offer |
| shortDesc | String | The short description of the offer |
| ownerId | Long | The ID of the offer's owner |
| price | Long | The price, in atomic units of the currency |
| currency | String | The currency in which the offer's price is set |

### Review
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the review |
| offerId | Long | The ID of the offer this review is about |
| reviewerId | Long | The ID of the reviewing user |
| rating | Int | The rating on a scale from 1 to 5 |
| comment | String | The text of the review |

### Trade
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the trade |
| offerId | Long | The ID of the offer this trade is about |
| price | Long | Price set at trade open, in piconero |
| buyerId | Long | The ID of the buyer in this trade (the seller is the offer owner) |

<!-- TODO: sending photos/videos -->
### TradeMessage
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the message |
| tradeId | Long | The ID of the trade this message was sent in |
| authorId | Long | The ID of the message author |
| isAdmin | Bool | Is this a message from an admin |
| date | DateTime | The date and time the message was sent |
| content | String | The content of the message |
