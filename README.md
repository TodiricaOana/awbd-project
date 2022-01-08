# Programare-Web-Java

Project details

Jewelry Online Shop

Business requirements:

1.	An unauthenticated user can register
2.	An authenticated user can update information in his profile and can delete his account
3.	An admin can see/delete registered users
4.	An admin can see/add/update/delete products and their characteristics 
5.	All users can see all the products and their characteristics
6.	All users can see all the products from a category (by type)
7.	An authenticated user can add/delete items in cart
8.	An admin can see all carts or a cart by user
9.	An authenticated user can place an order and cancel the order, if needed (if the products he wants to order are not in stock, he can't place the order)
10.	An authenticated user can see all the reviews, can create and update his reviews about products
11.	An admin can see all the reviews for products and can delete reviews (if they are unethical)
12.	An admin can see orders from all users or from a specific user and can update orders

Main features: 

1.	Management of users and personal information
2.	Management of products
3.	Management of reviews
4.	Management of cart
5.	Management of orders
6.	Management of reviews

For Swagger UI go to this site, while running the application: http://localhost:8080/swagger-ui.html

For Swagger documentation call this url http://localhost:8080/v3/api-docs and it will return:

{
    "openapi": "3.0.1",
    "info": {
        "title": "OpenAPI definition",
        "version": "v0"
    },
    "servers": [
        {
            "url": "http://localhost:8080",
            "description": "Generated server url"
        }
    ],
    "paths": {
        "/api/user/update/{id}": {
            "put": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "updateUser",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/UserDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/UserDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/review/update/{id}": {
            "put": {
                "tags": [
                    "review-controller"
                ],
                "operationId": "updateReview",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/ReviewDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ReviewDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/update/{id}": {
            "put": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "updateProduct",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/ProductDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ProductDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/update/{id}/{status}": {
            "put": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "updateOrder",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    {
                        "name": "status",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/OrderDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/cart/delete/{cartId}/{productId}": {
            "put": {
                "tags": [
                    "cart-controller"
                ],
                "operationId": "deleteProductFromCart",
                "parameters": [
                    {
                        "name": "cartId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    {
                        "name": "productId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/CartDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/user/register": {
            "post": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "createUser",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/UserDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/UserDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/review/add": {
            "post": {
                "tags": [
                    "review-controller"
                ],
                "operationId": "addReview",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/ReviewDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ReviewDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/add/{productType}": {
            "post": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "addProduct",
                "parameters": [
                    {
                        "name": "productType",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "enum": [
                                "BRACELET",
                                "CHAIN",
                                "RING",
                                "EARRINGS"
                            ]
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/ProductDto"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ProductDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/add/{userId}": {
            "post": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "placeOrder",
                "parameters": [
                    {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/OrderDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/cart/add/{userId}/{productId}": {
            "post": {
                "tags": [
                    "cart-controller"
                ],
                "operationId": "addToCart",
                "parameters": [
                    {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    {
                        "name": "productId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/CartDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/user/{id}": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "get",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/UserDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/user/all": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "get_1",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/UserDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/review/get/{productId}": {
            "get": {
                "tags": [
                    "review-controller"
                ],
                "operationId": "getByProductType",
                "parameters": [
                    {
                        "name": "productId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/ReviewDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/{id}": {
            "get": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "get_2",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ProductDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/get/{type}": {
            "get": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "getByProductType_1",
                "parameters": [
                    {
                        "name": "type",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "enum": [
                                "BRACELET",
                                "CHAIN",
                                "RING",
                                "EARRINGS"
                            ]
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/ProductDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/all": {
            "get": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "get_3",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/ProductDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/{orderId}": {
            "get": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "getOrderById",
                "parameters": [
                    {
                        "name": "orderId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/OrderDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/user/{userId}": {
            "get": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "getOrdersByUser",
                "parameters": [
                    {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/OrderDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/all": {
            "get": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "get_4",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/OrderDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/cart/{userId}": {
            "get": {
                "tags": [
                    "cart-controller"
                ],
                "operationId": "getCartByUser",
                "parameters": [
                    {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/CartDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/cart/all": {
            "get": {
                "tags": [
                    "cart-controller"
                ],
                "operationId": "get_5",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "type": "array",
                                    "items": {
                                        "$ref": "#/components/schemas/CartDto"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/user/delete/{id}": {
            "delete": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "delete",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/UserDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/review/delete/{id}": {
            "delete": {
                "tags": [
                    "review-controller"
                ],
                "operationId": "delete_1",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ReviewDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/product/delete/{id}": {
            "delete": {
                "tags": [
                    "product-controller"
                ],
                "operationId": "delete_2",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ProductDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/order/delete/{orderId}": {
            "delete": {
                "tags": [
                    "order-controller"
                ],
                "operationId": "deleteOrder",
                "parameters": [
                    {
                        "name": "orderId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/CartDto"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/cart/delete/{userId}": {
            "delete": {
                "tags": [
                    "cart-controller"
                ],
                "operationId": "deleteCartByUserId",
                "parameters": [
                    {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/CartDto"
                                }
                            }
                        }
                    }
                }
            }
        }
    },
    "components": {
        "schemas": {
            "UserDto": {
                "required": [
                    "address",
                    "email",
                    "fullName",
                    "password",
                    "phoneNumber"
                ],
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "fullName": {
                        "type": "string"
                    },
                    "email": {
                        "type": "string"
                    },
                    "password": {
                        "type": "string"
                    },
                    "address": {
                        "type": "string"
                    },
                    "phoneNumber": {
                        "type": "string"
                    },
                    "otherInformation": {
                        "type": "string"
                    }
                }
            },
            "ReviewDto": {
                "required": [
                    "productId",
                    "text",
                    "userId"
                ],
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "text": {
                        "type": "string"
                    },
                    "userId": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "productId": {
                        "type": "integer",
                        "format": "int64"
                    }
                }
            },
            "ProductDto": {
                "required": [
                    "name",
                    "numberOfProducts",
                    "price"
                ],
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "name": {
                        "type": "string"
                    },
                    "price": {
                        "type": "number",
                        "format": "double"
                    },
                    "numberOfProducts": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "productType": {
                        "type": "string",
                        "enum": [
                            "BRACELET",
                            "CHAIN",
                            "RING",
                            "EARRINGS"
                        ]
                    }
                }
            },
            "OrderDto": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "status": {
                        "type": "string"
                    },
                    "userId": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "products": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/ProductDto"
                        }
                    }
                }
            },
            "CartDto": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "userId": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "products": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/ProductDto"
                        }
                    }
                }
            }
        }
    }
}
