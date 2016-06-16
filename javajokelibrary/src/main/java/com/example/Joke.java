package com.example;

public class Joke {

    /**
     * Jokes obtained from @see <a href="http://www.gotlines.com/jokes/">Gotlines.com</a>
     */
    private static final String[] jokes = {
            "What's the difference between snowmen and snowladies? Snowballs",
            "I am a nobody, nobody is perfect, therefore I am perfect.",
            "How do you make holy water? You boil the hell out of it.",
            "Why did the blonde get excited after finishing her puzzle in 6 months? -- The box said 2-4 years!",
            "What do you call a bear with no teeth? -- A gummy bear!",
            "I once farted in an elevator, it was wrong on so many levels.",
            "If con is the opposite of pro, it must mean Congress is the opposite of progress?",
            "I wondered why the frisbee was getting bigger, and then it hit me.",
            "My mom never saw the irony in calling me a son-of-a-bitch.",
            "If 4 out of 5 people SUFFER from diarrhea; does that mean that one enjoys it?"
    };

    public static String getJoke() {
        int jokeIndex = (int)(Math.ceil((Math.random() * (jokes.length-1))));
        return jokes[jokeIndex];
    }

}
