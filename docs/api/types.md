# API Types
Partial types are referred to as `Type/<number>`, which means that it is `Type` only with fields
marked by superscript `<number>` in the table.

### Paginated(*T*?)
| Field | Type | Description |
| ----- | ---- | ----------- |
| pageno | Integer | The 0-based index of the requested page |
| pagesz | Integer | The page size |
| body? | *T* | The main body of the request |

### Page(*T*)
| Field | Type | Description |
| ----- | ---- | ----------- |
| items | *T*[] | The items on this page |
| total | Integer | The total number of items at this endpoint |

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
| title <sup>1</sup> | String | The title of the offer |
| shortDesc <sup>1</sup> | String | The short description of the offer |
| ownerId | Long | The ID of the offer's owner |
| price <sup>1</sup> | Long | The price, in atomic units of the currency |
| currency <sup>1</sup> | String | The currency in which the offer's price is set |

### Review
| Field | Type | Description |
| ----- | ---- | ----------- |
| id | Long | The ID of the review |
| offerId | Long | The ID of the offer this review is about |
| reviewerId | Long | The ID of the reviewing user |
| rating <sup>1</sup> | Int | The rating on a scale from 1 to 5 |
| comment <sup>1</sup> | String | The text of the review |

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
| content <sup>1</sup> | String | The content of the message |

### SearchRequest
| Field | Type | Description |
| ----- | ---- | ----------- |
| query | String | The search query string |
| type | String | Which type of entity to search for |
| sortBy | String | Which attribute of the entity to sort by |
| desc? | Bool | If true, sort in descending order. If false or not present, sort in ascending order |
<!-- | filters | SearchFilter[] | Filters to apply to the search |

### SearchFilter
| Field | Type | Description |
| ----- | ---- | ----------- |
| attibute | String | The attribute of the entity to filter by |
| -->
