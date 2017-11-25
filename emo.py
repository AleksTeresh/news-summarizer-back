import json
import operator
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 import Features, EntitiesOptions, KeywordsOptions, EmotionOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username="d5239c8a-2d28-4008-8b62-d0bbb3c0d8f9",
  password="dnPfrTAFkMf5",
  version="2017-02-27")

response = natural_language_understanding.analyze(text=sys.argv[1], features=Features(emotion=EmotionOptions()))
response_document = list(response.values())[2]
emotions = response_document['document']['emotion']
sorted_emotions = sorted(emotions.items(), key=operator.itemgetter(1), reverse=True)
return_list = []
is_there_over_limit = False
for emotion in sorted_emotions:
    if emotion[1] > 0.5:
        return_list.append(emotion[0])
        is_there_over_limit = True
if is_there_over_limit == False:
    _emotion = sorted_emotions[0]
    return_list.append(_emotion[0])
print(json.dumps(return_list))
