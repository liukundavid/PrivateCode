import sys, re, time
import operator

# read files from disk
# return an array of string
def read_file(filename, is_array):
	with open (filename, "r", 1) as myfile:
		raw_str = myfile.read().strip().lower()
		# convert the raw string to array
		array = raw_str.split('\n')
	print len(array)
	myfile.close()
	if is_array:
		return array
	else:
		return raw_str
		# return re.sub('\n', ' ', raw_str)

def ngram_algorithm(n, query, tweet):
	sub_queries = []
	sub_tweets = []

	if len(query) <= n:
		sub_queries.append(query)
	
	for i in xrange(len(query)):
		if i + n > len(query):
			break
		sub_queries.append(query[i:i+n])

	if len(tweet) <= n:
		sub_queries.append(tweet)

	for i in xrange(len(tweet)):
		if i + n > len(tweet):
			break
		sub_tweets.append(tweet[i:i+n])
	# print sub_queries, sub_tweets
	num_common_subs = 0
	for i in xrange(len(sub_queries)):
		for j in xrange(len(sub_tweets)):
			if sub_queries[i] == sub_tweets[j]:
				num_common_subs += 1
				sub_tweets[j] = ""
				break
	return len(sub_queries) + len(sub_tweets) - 2*num_common_subs
		

def global_edit_distance_algorithm(query, tweet):
	q_l = len(query) + 1
	t_l = len(tweet) + 1

	# declare the distance 
	distance = [[0 for x in range(t_l)] for x in range(q_l)]

	# build the global distance matrix, initialize the distance matrix
	for i in range(t_l):
		distance[0][i] = i
	for j in range(q_l):
		distance[j][0] = j
	# print distance
	
	for i in range(1,t_l):
		for j in range(1,q_l):
			distance[j][i] = min3(distance[j-1][i] + 1, 
				distance[j][i-1] + 1, 
				distance[j-1][i-1] + equal(query[j-1], tweet[i-1]))

	# print the matrix
	# print_distance_matrix(distance, q_l, t_l)

	return distance[len(query)][len(tweet)]

def print_distance_matrix(distance, q_l, t_l):
	for i in range(q_l):
		for j in range(t_l):
			sys.stdout.write(str(distance[i][j]) + " ")
		print

def min3(a, b, c):
	if a >= b:
		a = b
	if a >= c:
		a = c
	return a

def equal(a, b):
	if a == b:
		return 0
	else:
		return 1

def main(argv):
	if argv.__len__() >= 2:
		method = argv[1]
	else:
		print 'method name error, please input 3gram, 2gram or gd'
		sys.exit()

	# change the file path here
	queries = read_file("./project1-data/query/queries.txt", 1)
	tweets = read_file("./project1-data/tweets/tweets.txt", 1)

	f = open(method + '_result.txt', 'w')
	time_start = time.clock()
	count = 0
	for query in queries:
		ranked_tweets = {}
		q_tokens = query.split()
		for tweet in tweets:
			t_tokens = tweet.split()
			approx_match = 0
			for q_token in q_tokens:
				for idx, t_token in enumerate(t_tokens):
					if method == '3gram':
						distance = ngram_algorithm(3, q_token, t_token)
					elif method == '2gram':
						distance = ngram_algorithm(2, q_token, t_token)
					elif method == 'gd':
						distance = global_edit_distance_algorithm(q_token, t_token)
					else:
						print 'method name error, please input 3gram, 2gram or gd'
					if distance < q_token.__len__() * 0.2:
						# print q_token + "=================" + t_token
						approx_match += 1
			ratio = approx_match / float(q_tokens.__len__())
			# print ratio
			if ratio >= 0.5:
				# print "tweet:" + str(t_tokens)
				ranked_tweets[t_tokens[0]] = ratio
		sorted_ranked_tweets = sorted(ranked_tweets.iteritems(), key=operator.itemgetter(1), reverse=True)
		if len(sorted_ranked_tweets) > 0:
			count += 1
			f.write(str(count) + " : " + query + "\t the first 5 approximate match tweet" + str(sorted_ranked_tweets[:5]) + '\n')
	time_elapsed = (time.clock() - time_start)
	f.write("time consumed: " + str(time_elapsed))
	f.close()

if __name__ == "__main__":
   main(sys.argv)

