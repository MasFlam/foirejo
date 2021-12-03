# Reviews Resource
- Path prefix: `/api/reviews`

### GET `/{reviewId}`
- Response body: `Review`
- Not authenticated

### DELETE `/{reviewId}`
Delete the review.
- Required role: `admin`

### GET `/{reviewId}/reporters`
Get all users who reported the review as inappropriate.
- Response body: `User[]`
- Required role: `admin`

### POST `/{reviewId}/report`
Report the review as inappropriate.

### POST `/{reviewId}/unreport`
Mark the review as appropriate.
- Required roles: `admin`
