# Exchangegif

**Exchangegif** is a simple application that returns a gif image from [giphy.com](https://giphy.com) depending on the rate of the selected currency.
The source of data on currencies is the [openexchangerates.org](https://openexchangerates.org/).

## Docker

To build docker image run following command

```
docker build -t exchangegif .
```
Run container with
```
docker run -p 8080:8080 -d exchangegif
```

## Usage

Application has one simple endpoint, which returns a gif image as a byte array.

```
GET http://localhost:8080/currency
```

where *currency* is case-insensitive 3-letter ISO code (e.g. EUR).

