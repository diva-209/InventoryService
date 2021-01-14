# InventoryService

Endpoint urls :

POST : http://localhost:8080/api/v1/addStoreDetails
GET : http://localhost:8080/api/v1/stores
PUT : http://localhost:8080/api/v1/updateStoresInfo
DELETE : http://localhost:8080/api/v1/storeInfo/{storeId}
GET BY storeId : http://localhost:8080/api/v1/storeInfo/{storeId}

1. Sample Payload for multiple Stores post request.

[
	{
		"storeId": 99319,
		"refund": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"delivery": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"sale": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		]
	},
	{
		"storeId": 99308,
		"refund": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"delivery": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"sale": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		]
	}
]

2. Sample pay load for Single post request.

[
	{
		"storeId": 99349,
		"refund": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"delivery": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		],
		"sale": [
			{
				"itemId": 725896412345,
				"itemName": "T-shirt",
				"quantity": 4
			}
		]
	}
]
