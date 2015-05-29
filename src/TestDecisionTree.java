import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.util.List;

/** 
 * 
 * @author swethaprasad
 *
 *This class tests the decision tree. 
 *It uses Groovy utilities to run the if else statements, which were constructed while building the decision tree
 */

public class TestDecisionTree {





	/**
	 * 
	 * @param exampleSet : input set - either training set or test set
	 * @param decisionTreeExpression : if else statements constructed while building decision tree
	 * @return
	 */
	public double testDecisionTree(ExampleSet exampleSet, List<String> decisionTreeExpressionList){

		Binding binding = new Binding();
		binding.setVariable("exampleSet", exampleSet);


		int rightPrediction=0,wrongPrediction=0;
		int votedClass=0;

		//Saves array of predictions made by each of the decision tree.
		int[] classValues=new int[decisionTreeExpressionList.size()];
		binding.setVariable("classValues", classValues);
		String decisionTreeExpression=null;

		//repeat for number of test examples in testset
		for(int i=0;i<exampleSet.getTrainingExamples().size();i++){
			//repeat for each of the decision trees, calculate the prediction.
			for(int j=0;j<decisionTreeExpressionList.size();j++){
				GroovyShell shell = new GroovyShell(new GroovyClassLoader(),binding);	
				binding.setVariable("i", i);
				binding.setVariable("j", j);
				decisionTreeExpression=decisionTreeExpressionList.get(j);
				String initializationString="TrainingExampleModel example = exampleSet.getTrainingExamples().get(i) ;Integer[] attr=example.getAttr();int classvalue=example.getClassLabel();";
				decisionTreeExpression=initializationString.concat(decisionTreeExpression);

				shell.evaluate(decisionTreeExpression);

			}
			//find the most predicted class label
			votedClass=this.voteClassLabel((int[])binding.getVariable("classValues"));
			//if the most predicted class label is equal to class label , then increment right prediction else increment wrong [rediction
			if(votedClass==exampleSet.getTrainingExamples().get(i).getClassLabel()){
				rightPrediction++;
			}else{
				wrongPrediction++;
			}
		}


		return ((double)rightPrediction/(rightPrediction+wrongPrediction)*100);


	}

	//calculates most predicted class label.
	private int voteClassLabel(int[] classLabels){
		int countOne=0,countZero=0;

		for(int classlabel:classLabels){
			if(classlabel==0){
				countZero++;
			}else{
				countOne++;
			}
		}
		if(countZero>countOne){
			return 0;
		}else{
			return 1;
		}

	}
}
