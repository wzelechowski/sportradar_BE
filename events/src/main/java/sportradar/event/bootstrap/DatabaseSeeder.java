package sportradar.event.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sportradar.event.club.model.Club;
import sportradar.event.club.repository.ClubRepository;
import sportradar.event.competition.model.Competition;
import sportradar.event.competition.model.SportType;
import sportradar.event.competition.repository.CompetitionRepository;
import sportradar.event.stadium.model.Stadium;
import sportradar.event.stadium.repository.StadiumRepository;
import sportradar.event.event.model.Event;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final CompetitionRepository competitionRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (clubRepository.count() > 0) {
            return;
        }

        Competition championsLeague = new Competition();
        championsLeague.setOriginId("uefa-champions-league");
        championsLeague.setOriginName("UEFA Champions League");
        championsLeague.setSportType(SportType.FOOTBALL);
        competitionRepository.save(championsLeague);

        Competition nba = new Competition();
        nba.setOriginId("nba");
        nba.setOriginName("NBA");
        nba.setSportType(SportType.BASKETBALL);
        competitionRepository.save(nba);

        Stadium campNou = new Stadium();
        campNou.setName("Spotify Camp Nou");
        campNou.setCapacity(99354);
        stadiumRepository.save(campNou);

        Stadium bernabeu = new Stadium();
        bernabeu.setName("Santiago Bernabéu");
        bernabeu.setCapacity(81044);
        stadiumRepository.save(bernabeu);

        Stadium oldTrafford = new Stadium();
        oldTrafford.setName("Old Trafford");
        oldTrafford.setCapacity(74310);
        stadiumRepository.save(oldTrafford);

        Stadium cryptoArena = new Stadium();
        cryptoArena.setName("Crypto.com Arena");
        cryptoArena.setCapacity(19067);
        stadiumRepository.save(cryptoArena);

        Club realMadrid = new Club();
        realMadrid.setName("Real Madrid");
        realMadrid.setOfficialName("Real Madrid CF");
        realMadrid.setClubCountryCode("ESP");
        realMadrid.setAbbreviation("RMA");
        realMadrid.setSlug("real-madrid-cf");
        clubRepository.save(realMadrid);

        Club barcelona = new Club();
        barcelona.setName("FC Barcelona");
        barcelona.setOfficialName("FC Barcelona");
        barcelona.setClubCountryCode("ESP");
        barcelona.setAbbreviation("FCB");
        barcelona.setSlug("fc-barcelona");
        clubRepository.save(barcelona);

        Club manUnited = new Club();
        manUnited.setName("Manchester United");
        manUnited.setOfficialName("Manchester United FC");
        manUnited.setClubCountryCode("ENG");
        manUnited.setAbbreviation("MUN");
        manUnited.setSlug("manchester-united");
        clubRepository.save(manUnited);

        Club lakers = new Club();
        lakers.setName("LA Lakers");
        lakers.setOfficialName("Los Angeles Lakers");
        lakers.setClubCountryCode("USA");
        lakers.setAbbreviation("LAL");
        lakers.setSlug("la-lakers");
        clubRepository.save(lakers);

        Club bulls = new Club();
        bulls.setName("Chicago Bulls");
        bulls.setOfficialName("Chicago Bulls");
        bulls.setClubCountryCode("USA");
        bulls.setAbbreviation("CHI");
        bulls.setSlug("chicago-bulls");
        clubRepository.save(bulls);

        Event match1 = new Event();
        match1.setSeason(2026);
        match1.setEventDate(LocalDateTime.now().plusDays(7));
        match1.setEventStatus(EventStatus.valueOf("SCHEDULED"));
        match1.setStage(sportradar.event.event.model.Stage.valueOf("SEMI_FINALS"));
        match1.setCompetition(championsLeague);
        match1.setStadium(bernabeu);

        sportradar.event.eventClub.model.EventClub ec1Real = new sportradar.event.eventClub.model.EventClub();
        ec1Real.setClub(realMadrid);
        ec1Real.setIsHome(true);
        ec1Real.setEvent(match1);

        sportradar.event.eventClub.model.EventClub ec1Barca = new sportradar.event.eventClub.model.EventClub();
        ec1Barca.setClub(barcelona);
        ec1Barca.setIsHome(false);
        ec1Barca.setEvent(match1);

        match1.setEventClubs(List.of(ec1Real, ec1Barca));
        eventRepository.save(match1);

        Event match2 = new Event();
        match2.setSeason(2026);
        match2.setEventDate(LocalDateTime.now().minusDays(2));
        match2.setEventStatus(EventStatus.valueOf("PLAYED"));
        match2.setStage(sportradar.event.event.model.Stage.valueOf("QUARTER_FINALS"));
        match2.setCompetition(championsLeague);
        match2.setStadium(oldTrafford);

        sportradar.event.eventClub.model.EventClub ec2ManUtd = new sportradar.event.eventClub.model.EventClub();
        ec2ManUtd.setClub(manUnited);
        ec2ManUtd.setIsHome(true);
        ec2ManUtd.setIsWinner(true);
        ec2ManUtd.setEvent(match2);

        sportradar.event.goal.model.Goal goal1 = new sportradar.event.goal.model.Goal();
        goal1.setMinute(15);
        goal1.setEventClub(ec2ManUtd);

        sportradar.event.goal.model.Goal goal2 = new sportradar.event.goal.model.Goal();
        goal2.setMinute(88);
        goal2.setEventClub(ec2ManUtd);

        ec2ManUtd.setGoals(List.of(goal1, goal2));

        sportradar.event.card.model.Card card1 = new sportradar.event.card.model.Card();
        card1.setMinute(45);
        card1.setCardType(sportradar.event.card.model.CardType.valueOf("YELLOW"));
        card1.setEventClub(ec2ManUtd);

        ec2ManUtd.setCards(List.of(card1));

        sportradar.event.eventClub.model.EventClub ec2Real = new sportradar.event.eventClub.model.EventClub();
        ec2Real.setClub(realMadrid);
        ec2Real.setIsHome(false);
        ec2Real.setIsWinner(false);
        ec2Real.setEvent(match2);

        sportradar.event.goal.model.Goal goal3 = new sportradar.event.goal.model.Goal();
        goal3.setMinute(33);
        goal3.setEventClub(ec2Real);

        ec2Real.setGoals(List.of(goal3));

        sportradar.event.card.model.Card card2 = new sportradar.event.card.model.Card();
        card2.setMinute(70);
        card2.setCardType(sportradar.event.card.model.CardType.valueOf("RED"));
        card2.setEventClub(ec2Real);

        ec2Real.setCards(List.of(card2));

        match2.setEventClubs(List.of(ec2ManUtd, ec2Real));
        eventRepository.save(match2);

        Event match3 = new Event();
        match3.setSeason(2026);
        match3.setEventDate(LocalDateTime.now().plusDays(2));
        match3.setEventStatus(EventStatus.valueOf("SCHEDULED"));
        match3.setStage(sportradar.event.event.model.Stage.valueOf("FINAL"));
        match3.setCompetition(nba);
        match3.setStadium(cryptoArena);

        sportradar.event.eventClub.model.EventClub ec3Lakers = new sportradar.event.eventClub.model.EventClub();
        ec3Lakers.setClub(lakers);
        ec3Lakers.setIsHome(true);
        ec3Lakers.setEvent(match3);

        sportradar.event.eventClub.model.EventClub ec3Bulls = new sportradar.event.eventClub.model.EventClub();
        ec3Bulls.setClub(bulls);
        ec3Bulls.setIsHome(false);
        ec3Bulls.setEvent(match3);

        match3.setEventClubs(List.of(ec3Lakers, ec3Bulls));
        eventRepository.save(match3);
    }
}