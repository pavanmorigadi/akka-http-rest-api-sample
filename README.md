# akka-http-rest-api-sample


1. Rest API to List all research papers


HTTP Method and URL:

GET /api/research-papers


Response:

status code: 200 OK

[
    {
        "title": "Oracledemo1",
        "authors": [
            "author1",
            "author2"s
        ],
        "publication_date": "2017/01/02"
    },
    {
        "title": "Oracledemo2",
        "authors": [
            "author3",
            "author4"
        ],
        "publication_date": "2017/02/04"
    },
    {
        "title": "researchpaper3",
        "authors": [
            "author5"
        ],
        "publication_date": "20/06/2019"
    },
    {
        "title": "researchpaper4",
        "authors": [
            "author6"
        ],
        "publication_date": "25/06/2019"
    }
]



2. API to Search  research papers


HTTP Method and URL:

POST /api/research-papers/search

Request: (I Consider all search terms as optional and user can provide whatever applicable)

{
   "search-terms":{
      "title":"Oracle",
      "author":"",
      "start-date":"2017/01/01",
      "end-date":"2018/02/03",
      "text-contains":""
   }
}


Reponse:

status code: 200 OK

[
   {
      "title":"Oracledemo1",
      "authors":[
         "author1",
         "author2"
      ],
      "publication_date":"2017/01/02"
   },
   {
      "title":"Oracledemo2",
      "authors":[
         "author3",
         "author4"
      ],
      "publication_date":"2017/02/04"
   }
]



3.Retrieve a single complete paper by id

HTTP Method and URL:

GET /api/research-papers/{id}

Here I'll consider title as UniqueIdentifier.
For Example:
GET /api/research-papers/researchpaper3

Reponse:

status code: 200 OK

{
    "title": "researchpaper3",
    "authors": [
        "author5"
    ],
    "publication_date": "20/06/2019"
}

