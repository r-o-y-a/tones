(
s.waitForBoot {

~rate = [];

a = PathName(thisProcess.nowExecutingPath).pathOnly ++ "samples/";
~buffers = PathName(a).entries.collect{|path| Buffer.read(s, path.fullPath)};

SynthDef(\samplePlayer1, { | bufnum = 0, rate = 1, amp = 0, lfoFreq = 1, filterFreq = 1000, filterRes = 0.5, loop = 1, reverbMix = 0.5, pitchShift = 1, delayMaxTime = 0, delayTime = 0, decayTime = 0, fade = 1, start |

    var lfo = SinOsc.kr(lfoFreq).range(0.8, 1.2);
    var playbackRate = rate * lfo;

	var sound = PlayBuf.ar(2, bufnum, playbackRate, loop: loop, startPos: start, /*doneAction: Done.freeGroup*/);
	sound = sound *  amp;

	// delay
	sound = CombL.ar(sound, maxdelaytime: delayMaxTime, delaytime: delayTime, decaytime: decayTime);

	sound = LPF.ar(sound, filterFreq, filterRes);
    //sound = HPF.ar(sound, 200, 1);
	sound = FreeVerb.ar(sound, 0.3, 2) *fade;

    Out.ar(0, sound);
}).add;

s.sync;

//t = Toner.new(\samplePlayer1, \samplePlayer2, true); // run offline
t = Toner.new(\samplePlayer1, \samplePlayer2);
}
);



// static sample
(
h = Pdef(\pSample6, Pmono(
    \samplePlayer1,
    \bufnum, ~buffers[7].bufnum,
	\rate, 1,
	\lfoFreq, 0, //1
    \amp, 1,
	\filterFreq, 10000,
	\filterRes, 5,
	\loop, 1,
    \reverbMix, 0.3,
	\pitchShift, 0,
	\delayMaxTime, 0,
	\delayTime, 0,
	\decayTime, 0
));
h.play;
)
h.stop;




// "America" - Allen Ginsberg
p = t.t("America I have given you all and now I am nothing.", 1); // disappointment, gratitude
p = t.t("America two dollars and twentyseven cents January 17, 1956.", 1); // neutral, approval
p = t.t("I can’t stand my own mind.", 1); // disapproval, anger, annoyance, neutral, disappointment
p = t.t("America when will we end the human war?", 1); // TODO: curiosity, confusion
p = t.t("Go fuck yourself with your atom bomb.", 1); // anger, neutral, fear
// ------- static sample change, add secondary tone
p = t.t("I don’t feel good don’t bother me."); // approval, optimism, relief
p = t.t("I won’t write my poem till I’m in my right mind."); // realization, neutral, disapproval
p = t.t("America when will you be angelic?"); // curiosity, admiration
p = t.t("When will you take off your clothes?"); // neutral, curiosity, annoyance
p = t.t("When will you look at yourself through the grave?"); // neutral, curiosity, confusion
p = t.t("When will you be worthy of your million Trotskyites?"); //curiosity, confusion, annoyance
p = t.t("America why are your libraries full of tears?"); // curiosity, confusion, neutral
p = t.t("America when will you send your eggs to India?"); // curiosity, neutral
p = t.t("I’m sick of your insane demands."); // annoyance, anger
// -------- static sample change
p = t.t("When can I go into the supermarket and buy what I need with my good looks?"); // curiosity, neutral
p = t.t("America after all it is you and I who are perfect not the next world."); // neutral, annoyance, realization
p = t.t("Your machinery is too much for me."); // love, admiration
p = t.t("You made me want to be a saint."); // admiration, desire, amusement
p = t.t("There must be some other way to settle this argument."); // confusion, disapproval
p = t.t("Burroughs is in Tangiers I don’t think he’ll come back it’s sinister."); // disapproval, neutral
p = t.t("Are you being sinister or is this some form of practical joke?"); // curiosity, confusion, amusement
p = t.t("I’m trying to come to the point."); // desire, neutral, approval
p = t.t("I refuse to give up my obsession."); // disapproval, approval, annoyance
p = t.t("America stop pushing I know what I’m doing."); // annoyance, neutral, approval
p = t.t("America the plum blossoms are falling."); // neutral, sadness
// ------- static sample change
p = t.t("I haven’t read the newspapers for months, everyday somebody goes on trial for murder."); // disapproval, neutral, realization
p = t.t("America I feel sentimental about the Wobblies."); // sadness, grief, love
p = t.t("America I used to be a communist when I was a kid I’m not sorry."); // neutral, realization, approval
p = t.t("I smoke marijuana every chance I get."); // approval, neutral, amusement
p = t.t("I sit in my house for days on end and stare at the roses in the closet."); // admiration, realization, pride
p = t.t("When I go to Chinatown I get drunk and never get laid."); // neutral, realization, approval

p = t.t("My mind is made up there’s going to be trouble."); // fear, nervousness, annoyance

p = t.t("You should have seen me reading Marx."); // neutral
p = t.t("My psychoanalyst thinks I’m perfectly right."); // neutral, approval, realization
p = t.t("I won’t say the Lord’s Prayer."); // disapproval, neutral, approval
p = t.t("I have mystical visions and cosmic vibrations."); // neutral, surprise, excitement
p = t.t("America I still haven’t told you what you did to Uncle Max after he came over from Russia."); // neutral
p = t.t("I’m addressing you."); // neutral
p = t.t("Are you going to let your emotional life be run by Time Magazine?"); // caring, curiosity, neutral
p = t.t("I’m obsessed by Time Magazine."); // desire, approval, love
p = t.t("I read it every week."); // neutral, approval
p = t.t("Its cover stares at me every time I slink past the corner candystore."); // neutral, annoyance
p = t.t("I read it in the basement of the Berkeley Public Library."); // neutral, approval, realization
p = t.t("It’s always telling me about responsibility. Businessmen are serious. Movie producers are serious. Everybody’s serious but me."); // neutral, annoyance, realization
p = t.t("It occurs to me that I am America."); // realization, neutral
p = t.t("I am talking to myself again."); // neutral


p.at(\p1).stop;
p.at(\p2).stop;
p.at(\p3).stop;
p.at(\p4).stop;
p.at(\p5).stop;


