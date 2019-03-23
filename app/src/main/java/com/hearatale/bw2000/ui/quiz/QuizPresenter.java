package com.hearatale.bw2000.ui.quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.QuestionModel;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;
import com.hearatale.bw2000.ui.display_score.DisplayScoreActivity;
import com.hearatale.bw2000.ui.quiz_celebrate.QuizCelebrateActivity;
import com.hearatale.bw2000.ui.quiz_congrats.QuizCongratsActivity;
import com.hearatale.bw2000.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizPresenter<V extends QuizMvpView> extends BasePresenter<V> implements QuizMvpPresenter<V> {
    private List<String> listImageAnswer;
    private String correctAnswer;
    private String mCategory;
    private QuestionModel questionModel = new QuestionModel();
    private static final String PATH_SOUND_INCORRECT = "Quiz_Sounds/incorrect_answer.mp3";
    private List<String> listSoundQuestion;
    private List<String> pathAudioCorrect = new ArrayList<>();
    private float mTotalScore = 0;
    List<Item> dataQuiz = new ArrayList<>();

    @Override
    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    private AppDataManager appDataManager;

    @Override
    public List<Item> getData(String titleCategory) {
        return appDataManager.getDataDisplayItem(titleCategory);
    }

    QuizPresenter() {
        appDataManager = AppDataManager.getInstance();
        getListAudioCorrect();
        getTotalScore();
    }

    private String parseQuestionText(String correctAnswerText) {
        correctAnswerText = correctAnswerText.replace(".mp3", "");
        if (!mCategory.contains("addition")) {
            correctAnswerText = correctAnswerText.replaceAll("\\d", "");
            correctAnswerText = correctAnswerText.replace("_", " ");
            correctAnswerText = correctAnswerText.replace("}", "?");
        } else {
            String[] addition = correctAnswerText.split("_");
            correctAnswerText = addition[addition.length - 1];
        }
        return correctAnswerText;
    }

    public List<String> getListNameImage(String category) {

        if (category.contains("addition")) {
            AssetManager assets = Application.Context.getAssets(); // get app's AssetManager
            //get sound for question
            //get all the path of the files inside Animals folder
            String[] imagesPath = new String[0];
            try {
                imagesPath = assets.list(category);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> imagesPath1;
            if (imagesPath != null && imagesPath.length > 0) {
                imagesPath1 = new ArrayList<>(Arrays.asList(imagesPath));
                for (int i = 0; i < imagesPath1.size(); i++) {
                    if (!imagesPath1.get(i).contains(".png")) {
                        imagesPath1.remove(i);
                    }
                }
                Collections.shuffle(imagesPath1);
                return imagesPath1;
            }
            return null;

        } else {

            List<Item> data = AppDataManager.getInstance().getDataDisplayItem(category);
            List<String> imagesName = new ArrayList<>();

            for (Item item : data) {
                String path = item.getImageUri();

                String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                imagesName.add(name);
            }
            Collections.shuffle(imagesName);
            return imagesName;
        }

    }

    @Override
    public void resetAllScore() {
        List<String> listCategory = AppDataManager.getInstance().getListCategory();
        if (listCategory.size() > 0) {
            for (String category : listCategory) {
                AppDataManager.getInstance().setScore(category, 0);
            }
        }
    }

    @Override
    public void setScore(String category, float score, float totalScore) {

        mTotalScore = totalScore;
        if (mTotalScore >= Config.MAX_TOTAL_SCORE) {
            resetAllScore();
            getTotalScore();
        }

        if (score >= Config.MAX_TOTAL_SCORE) {
            AppDataManager.getInstance().setScore(category, 0);
        } else {
            AppDataManager.getInstance().setScore(category, score);
        }
    }

    @Override
    public float getTotalScore() {
        return mTotalScore = AppDataManager.getInstance().getTotalScore();
    }

    @Override
    public int getCurrentPraiseIndex() {
        return getDataManager().getCurrentPraiseIndex();
    }

    @Override
    public void setCurrentPraiseIndex(int index) {
        getDataManager().setCurrentPraiseIndex(index);
    }

    @Override
    public List<Item> getAllMp3(String category) {
        List<Item> listItemMp3 = new ArrayList<>();
        if (category.contains("addition")) {
            List<String> soundPath1;
            AssetManager assets = Application.Context.getAssets(); // get app's AssetManager
            //get sound for question
            //get all the path of the files inside Animals folder
            String[] soundPath = new String[0];
            try {
                soundPath = assets.list(category + "/sounds");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (soundPath != null && soundPath.length > 0) {
                soundPath1 = new ArrayList<>(Arrays.asList(soundPath));

                for (String s : soundPath) {
                    Item item = new Item();
                    item.setImageUri(s);
                    listItemMp3.add(item);
                }
                Collections.shuffle(listItemMp3);
                return listItemMp3;
            }
            return null;
        } else {

            List<Item> data = AppDataManager.getInstance().getDataDisplayItem(category);
            List<String> soundsPath = new ArrayList<>();

            for (Item item : data) {
                String path = item.getImageUri();

                String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                String replace = name.replace(".png", ".mp3");


                soundsPath.add(replace);
            }
            Collections.shuffle(soundsPath);
//            return soundsPath;
            return null;

        }
    }

    @Override
    public void updateQuestion(String category) {
        if (dataQuiz == null || dataQuiz.size() < 4) {
            dataQuiz.clear();
            dataQuiz.addAll(getData(category));
            if (category.contains("addition")) {
                int i = 29;
                for (Item item : dataQuiz) {
                    String urlImage = item.getImageUri();
                    String temp = urlImage.substring(0, urlImage.lastIndexOf("/") + 1) + "addition_quiz/";
                    urlImage = temp + item.getTitle().substring(4, item.getTitle().length()) + ".png";

                    item.setTitle(i+"_"+item.getTitle().substring(0, 4));
                    item.setImageUri(urlImage);

                    i++;
                }
            }
        }
        Collections.shuffle(dataQuiz);
        Item correct = dataQuiz.get(0);
        correct.setAnswer(true);
        String name = correct.getImageUri().substring(correct.getImageUri().lastIndexOf("/") + 1, correct.getImageUri().length());
        String replace = name.replace(".png", ".mp3");
        if (category.contains("addition")){
            correctAnswer = "/addition_quiz/sounds/"+correct.getTitle()+".mp3";
        }else {
            correctAnswer = replace;
        }
        dataQuiz.remove(0);
        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion(parseQuestionText(correct.getTitle()));
        List<Item> data = new ArrayList<>();
        data.add(correct);
        data.add(dataQuiz.get(0));
        data.add(dataQuiz.get(1));
        data.add(dataQuiz.get(2));

        Collections.shuffle(data);
        questionModel.setData(data);

        getMvpView().finishUpdateQuestion(questionModel);


//        if (listSoundQuestion == null || listSoundQuestion.size() <= 0) {
//            listSoundQuestion = getAllMp3(category);
//        }
//        this.mCategory = category;
//        List<AnswerModel> answerModelList = new ArrayList<>();
//        if (listSoundQuestion == null) return;
//        getAllImage();
//        if (listSoundQuestion.size() == 0) {
//            listSoundQuestion = getAllMp3(category);
//        }
//        //correct answer
//        if (listSoundQuestion != null && listSoundQuestion.size() > 0) {
//
//            if (correctAnswer != null && correctAnswer.equals(listSoundQuestion.get(0))) {
//                correctAnswer = listSoundQuestion.get(1);
//                //remove from list
//                listSoundQuestion.remove(1);
//            } else {
//                correctAnswer = listSoundQuestion.get(0);
//                //remove from list
//                listSoundQuestion.remove(0);
//            }
//
//            if (this.mCategory.contains("addition")) {
//                String temp = correctAnswer;
//                temp = parseQuestionText(temp);
//                int number1 = Integer.valueOf(temp.substring(0, 1));
//                int number2 = Integer.valueOf(temp.substring(2, 3));
//                listImageAnswer.remove((number1 + number2) + ".png");
//            } else {
//                listImageAnswer.remove(correctAnswer.replace(".mp3", ".png"));
//            }
//        }
//        if (listSoundQuestion != null) {
//            //create list AnswerModel
//            //correctAnswer = listSoundQuestion.get(0);
//            for (int i = 0; i <= listImageAnswer.size(); i++) {
//                //i = 0 correct answer
//                if (i == 0) {
//                    if (this.mCategory.contains("addition")) {
//                        String temp = correctAnswer;
//                        temp = parseQuestionText(temp);
//                        int number1 = Integer.valueOf(temp.substring(0, 1));
//                        int number2 = Integer.valueOf(temp.substring(2, 3));
//                        String pathImage = category + "/" + (number1 + number2) + ".png";
//                        String nameImage = (number1 + number2) + ".png";
//                        answerModelList.add(new AnswerModel(nameImage, pathImage, true));
//                    } else {
//                        String pathImage = category + "/" + correctAnswer.replace("mp3", "png");
//                        String nameImage = correctAnswer.replace("mp3", "png");
//                        answerModelList.add(new AnswerModel(nameImage, pathImage, true));
//                    }
//                } else {
//                    String pathImage = category + "/" + listImageAnswer.get(i - 1);
//                    String nameImage = listImageAnswer.get(i - 1);
//                    answerModelList.add(new AnswerModel(nameImage, pathImage, false));
//                }
//                if (i == 3) break;
//            }
//            Collections.shuffle(answerModelList);
//            questionModel.setData(answerModelList);
//            questionModel.setQuestion(parseQuestionText(correctAnswer));
//            getMvpView().finishUpdateQuestion(questionModel);
//        }
    }

    @Override
    public void getAllImage() {
        listImageAnswer = getListNameImage(mCategory);
    }

    @Override
    public void playAudioQuestion() {
        if(mCategory.contains("addition")){
            AudioPlayerHelper.getInstance().playAudio(mCategory + correctAnswer);
        }else {
            AudioPlayerHelper.getInstance().playAudio(mCategory + "/sounds/" + correctAnswer);
        }
    }

    @Override
    public void playAudioCorrect(AudioPlayerHelper.DonePlayingListener listener) {
        AudioPlayerHelper.getInstance().playAudio("Quiz_Sounds/correct_answer.mp3", listener);
    }

    @Override
    public void playAudioInCorrect(AudioPlayerHelper.DonePlayingListener listener) {
        AudioPlayerHelper.getInstance().playAudio(PATH_SOUND_INCORRECT, listener);
    }

    @Override
    public void startCongratulations() {
        Intent intent = new Intent(getMvpView().getActivity(), QuizCongratsActivity.class);
        getMvpView().pushIntentForResult(intent, Config.REQUES_CODE);
    }

    @Override
    public void startCelebration(String type, int endX, int endY, int countTruck) {
        Intent intent = new Intent(getMvpView().getActivity(), QuizCelebrateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ENDX, endX);
        bundle.putInt(Config.ENDY, endY);
        bundle.putInt(Config.COUNT_TRUCK, countTruck);
        bundle.putString(Config.TYPE, type);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getMvpView().pushIntentForResult(intent, Config.REQUES_CODE);
    }

    @Override
    public QuestionModel exportDataQuestion() {
        return questionModel;
    }

    private void getListAudioCorrect() {
        AssetManager assets = Application.Context.getAssets(); // get app's AssetManager
        String path = "Quiz_Sounds/correct";
        try {
            //get congrats sounds
            //get all the path of the files inside the folder
            String[] congrat = assets.list(path);
            if (congrat != null) {
                pathAudioCorrect = new ArrayList<>(Arrays.asList(congrat));
            }
        } catch (IOException ignored) {

        }
    }

    //play audio with position list audio correct
    @Override
    public void playAudioCorrect(int position, AudioPlayerHelper.DonePlayingListener listener) {
        String path = "Quiz_Sounds/correct";
        position = position % pathAudioCorrect.size();
        AudioPlayerHelper.getInstance().playAudio(path + "/" + pathAudioCorrect.get(position), listener);
        setCurrentPraiseIndex(position);
    }

    @Override
    public void goToDisplayScore(boolean isAnimation) {
        Intent intent = new Intent(getMvpView().getActivity(), DisplayScoreActivity.class);
        intent.putExtra(Config.ANIMATION, isAnimation);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getMvpView().pushIntentForResult(intent, Config.REQUES_CODE_DISPLAY_SCORE);
    }
}
