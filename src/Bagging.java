import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates bagging using Math.random function
 * @author swethaprasad
 *
 */
public class Bagging {

	List<ExampleSet> createBags(ExampleSet trainingSet, int noOfbags){

		List<ExampleSet> sampleSet = new ArrayList<ExampleSet>();
		Random r = new Random();
		ExampleSet ex=null;
		//Repeat for number of bags
		for(int i=0;i<noOfbags;i++){
			ex= trainingSet.cloneForBagging();
			int random =0;
			//repeat for number of examples in the training set
			for(int j=0;j<trainingSet.getTrainingExamples().size();j++){
				// get a number between 0 and trainingset.size -1
				random = r.nextInt(trainingSet.getTrainingExamples().size());
				//get the training example at  index random and add it into new training set.
				ex.addTrainingExample(trainingSet.getTrainingExamples().get(random));
			}
			sampleSet.add(ex);
		}


		return sampleSet;
	}



}
