import json
import sys
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud import NaturalLanguageUnderstandingV1 as NaturalLanguageUnderstanding
from watson_developer_cloud.natural_language_understanding_v1 import Features, EntitiesOptions, KeywordsOptions, ConceptsOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username="d5239c8a-2d28-4008-8b62-d0bbb3c0d8f9",
  password="dnPfrTAFkMf5",
  version="2017-02-27")

response = natural_language_understanding.analyze(text=sys.argv[1],features=Features(concepts=ConceptsOptions(limit=int(sys.argv[2]))))
results = list(response.values())[2]
return_list = []
for result in results:
    return_list.append(result['text'])
print(json.dumps(return_list))
