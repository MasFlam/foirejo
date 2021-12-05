# Users Resource
- Path prefix: `/api/users`

### GET `/me`
- Response body: `User`

### GET `/me/offers`
- Response body: `Offer[]`

### GET `/me/offers/count`
- Response body: `Int`

### GET `/me/trades`
- Response body: `Trade[]`

### GET `/me/trades/count`
- Response body: `Int`

### GET `/{userId}`
- Response body: `User`
- Not authenticated

### GET `/{userId}/offers`
- Response body: `Offer[]`
- Not authenticated

### GET `/{userId}/offers/count`
- Response body: `Int`
- Not authenticated

### GET `/{userId}/rate`
Get the rating you gave the user.
- Response body: `true`, `false` or `null`

### POST `/{userId}/rate`
Rate the user. Can only be requested if you have traded with the user, unless you cancelled your
only shared trade before the seller marked it as completed.
- Request body: `true`, `false`

### DELETE `/{userId}/rate`
Remove your rating of the user. If you have not rated the user, nothing happens.
