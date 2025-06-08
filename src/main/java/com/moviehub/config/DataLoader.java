package com.moviehub.config;

import com.moviehub.entity.Movie;
import com.moviehub.entity.Series;
import com.moviehub.entity.User;
import com.moviehub.repository.MovieRepository;
import com.moviehub.repository.SeriesRepository;
import com.moviehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Uygulama başlatıldığında örnek verileri yükleyen sınıf
 * CommandLineRunner interface'ini implement eder
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private SeriesRepository seriesRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🎬 MovieHub DataLoader başlatılıyor...");
        
        // Kullanıcıları yükle
        loadUsers();
        
        // Filmleri yükle
        loadMovies();
        
        // Dizileri yükle
        loadSeries();
        
        System.out.println("✅ Örnek veriler başarıyla yüklendi!");
    }
    
    /**
     * Test kullanıcılarını yükler
     */
    private void loadUsers() {
        System.out.println("👥 Kullanıcılar kontrol ediliyor...");
        
        // Admin kullanıcısı
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@moviehub.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            
            System.out.println("🔧 Admin kullanıcısı oluşturuldu: admin / admin123");
        }
        
        // Test kullanıcısı
        if (!userRepository.existsByUsername("testuser")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@moviehub.com");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setRole(User.Role.USER);
            userRepository.save(testUser);
            
            System.out.println("👤 Test kullanıcısı oluşturuldu: testuser / test123");
        }
        
        // Ahmet kullanıcısı
        if (!userRepository.existsByUsername("ahmet")) {
            User ahmet = new User();
            ahmet.setUsername("ahmet");
            ahmet.setEmail("ahmet@example.com");
            ahmet.setPassword(passwordEncoder.encode("123456"));
            ahmet.setRole(User.Role.USER);
            userRepository.save(ahmet);
            
            System.out.println("👤 Ahmet kullanıcısı oluşturuldu: ahmet / 123456");
        }
        
        // Ayşe kullanıcısı
        if (!userRepository.existsByUsername("ayse")) {
            User ayse = new User();
            ayse.setUsername("ayse");
            ayse.setEmail("ayse@example.com");
            ayse.setPassword(passwordEncoder.encode("123456"));
            ayse.setRole(User.Role.USER);
            userRepository.save(ayse);
            
            System.out.println("👤 Ayşe kullanıcısı oluşturuldu: ayse / 123456");
        }
    }
    
    /**
     * Örnek filmleri yükler
     */
    private void loadMovies() {
        if (movieRepository.count() > 0) {
            System.out.println("🎬 Filmler zaten mevcut, atlanıyor...");
            return;
        }
        
        System.out.println("🎬 Örnek filmler yükleniyor...");
        
        // Hollywood Filmleri
        Movie movie1 = new Movie();
        movie1.setTitle("The Shawshank Redemption");
        movie1.setDescription("İki hapis arkadaşı yıllar boyunca bağ kurar, ortak insan yaşamı, umut ve kurtulma anlayışında sıradışı konfor ve eninde nihayet kurtuluş bulur.");
        movie1.setReleaseDate(LocalDate.of(1994, 9, 23));
        movie1.setGenre("Drama");
        movie1.setDirector("Frank Darabont");
        movie1.setDuration(142);
        movie1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg");
        movieRepository.save(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("The Godfather");
        movie2.setDescription("Güçlü bir Amerikan suç ailesinin yaşlı ataerkil figürünün, kontrolü gönülsüz oğluna devretmesi, aile ve şiddet temaları ile dolu destansı hikaye.");
        movie2.setReleaseDate(LocalDate.of(1972, 3, 24));
        movie2.setGenre("Crime, Drama");
        movie2.setDirector("Francis Ford Coppola");
        movie2.setDuration(175);
        movie2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
        movieRepository.save(movie2);

        Movie movie3 = new Movie();
        movie3.setTitle("The Dark Knight");
        movie3.setDescription("Batman, adalet için Gotham'ın suçu kabul ederken, bir sonraki nesil suçlularından biri olan Joker, halkı kaosa sürükler ve Batman'i süper kahramanlık kurallarını test etmeye zorlar.");
        movie3.setReleaseDate(LocalDate.of(2008, 7, 18));
        movie3.setGenre("Action, Crime, Drama");
        movie3.setDirector("Christopher Nolan");
        movie3.setDuration(152);
        movie3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg");
        movieRepository.save(movie3);

        Movie movie4 = new Movie();
        movie4.setTitle("Pulp Fiction");
        movie4.setDescription("Birbirine karışmış suçlu hikayeleri, Los Angeles'ta geçen ve zamanda ileri geri atlayan bu kultür filmi.");
        movie4.setReleaseDate(LocalDate.of(1994, 10, 14));
        movie4.setGenre("Crime, Drama");
        movie4.setDirector("Quentin Tarantino");
        movie4.setDuration(154);
        movie4.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
        movieRepository.save(movie4);

        Movie movie5 = new Movie();
        movie5.setTitle("Forrest Gump");
        movie5.setDescription("Alabama'dan basit fikirli bir adamın, hayatının olaylarının 20. yüzyılın tarihi olaylarını yansıtması.");
        movie5.setReleaseDate(LocalDate.of(1994, 7, 6));
        movie5.setGenre("Drama, Romance");
        movie5.setDirector("Robert Zemeckis");
        movie5.setDuration(142);
        movie5.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg");
        movieRepository.save(movie5);

        Movie movie6 = new Movie();
        movie6.setTitle("Inception");
        movie6.setDescription("Rüya çalma teknolojisini kullanan bir hırsız, zihne fikir eklemek için son görevini gerçekleştirmek zorundadır.");
        movie6.setReleaseDate(LocalDate.of(2010, 7, 16));
        movie6.setGenre("Action, Sci-Fi, Thriller");
        movie6.setDirector("Christopher Nolan");
        movie6.setDuration(148);
        movie6.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg");
        movieRepository.save(movie6);

        // Türk Filmleri
        Movie turkishMovie1 = new Movie();
        turkishMovie1.setTitle("Ayla: The Daughter of War");
        turkishMovie1.setDescription("Kore Savaşı sırasında Türk askerinin bulduğu küçük kızla kurduğu baba-kız ilişkisinin dokunaklı hikayesi.");
        turkishMovie1.setReleaseDate(LocalDate.of(2017, 10, 27));
        turkishMovie1.setGenre("Drama, War");
        turkishMovie1.setDirector("Can Ulkay");
        turkishMovie1.setDuration(125);
        turkishMovie1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMjI3MzM3NzIzNV5BMl5BanBnXkFtZTgwNDI4MDM1MzI@._V1_.jpg");
        movieRepository.save(turkishMovie1);

        Movie turkishMovie2 = new Movie();
        turkishMovie2.setTitle("Müslüm");
        turkishMovie2.setDescription("Türk sanat müziğinin efsane ismi Müslüm Gürses'in yaşam öyküsü.");
        turkishMovie2.setReleaseDate(LocalDate.of(2018, 10, 26));
        turkishMovie2.setGenre("Biography, Drama, Music");
        turkishMovie2.setDirector("Can Ulkay");
        turkishMovie2.setDuration(137);
        turkishMovie2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNjZkNzI0NDQtZjZmOS00YWE3LWJiZWEtZWVmOGM4NGI0ZGFhXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg");
        movieRepository.save(turkishMovie2);

        Movie turkishMovie3 = new Movie();
        turkishMovie3.setTitle("Miracle in Cell No. 7");
        turkishMovie3.setDescription("Zihinsel engelli bir babanın kızı için verdiği mücadelenin ve adalet arayışının dokunaklı hikayesi.");
        turkishMovie3.setReleaseDate(LocalDate.of(2019, 10, 11));
        turkishMovie3.setGenre("Drama, Family");
        turkishMovie3.setDirector("Mehmet Ada Öztekin");
        turkishMovie3.setDuration(132);
        turkishMovie3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNjRlNjg3OTQtNjJkNi00YjIzLWI0YjUtYzkyNTE4MTA1NWU0XkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg");
        movieRepository.save(turkishMovie3);

        Movie actionMovie = new Movie();
        actionMovie.setTitle("John Wick");
        actionMovie.setDescription("Emekli bir suikastçı, köpeğini öldüren kişilerin peşine düşer ve yeraltı dünyasına geri döner.");
        actionMovie.setReleaseDate(LocalDate.of(2014, 10, 24));
        actionMovie.setGenre("Action, Crime, Thriller");
        actionMovie.setDirector("Chad Stahelski");
        actionMovie.setDuration(101);
        actionMovie.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_.jpg");
        movieRepository.save(actionMovie);

        System.out.println("✅ " + movieRepository.count() + " film yüklendi!");
    }
    
    /**
     * Örnek dizileri yükler
     */
    private void loadSeries() {
        if (seriesRepository.count() > 0) {
            System.out.println("📺 Diziler zaten mevcut, atlanıyor...");
            return;
        }
        
        System.out.println("📺 Örnek diziler yükleniyor...");
        
        // Popüler Yabancı Diziler
        Series series1 = new Series();
        series1.setTitle("Breaking Bad");
        series1.setDescription("Lise kimya öğretmeni Walter White, terminal kanser teşhisi konulduktan sonra, ailesinin finansal geleceğini güvence altına almak için metamfetamin üretmeye ve satmaya başlar.");
        series1.setReleaseDate(LocalDate.of(2008, 1, 20));
        series1.setGenre("Crime, Drama, Thriller");
        series1.setSeasons(5);
        series1.setEpisodes(62);
        series1.setStatus("Completed");
        series1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BYmQ4YWMxYjUtNjZmYi00MDQ1LWFjMjMtNjA5ZDdiYjdiODU5XkEyXkFqcGdeQXVyMTMzNDExODE5._V1_.jpg");
        seriesRepository.save(series1);

        Series series2 = new Series();
        series2.setTitle("Game of Thrones");
        series2.setDescription("Westeros'un yedi krallığında, aileler Iron Throne'un kontrolü için savaşırken, eski düşmanlar kuzeyden geri döner.");
        series2.setReleaseDate(LocalDate.of(2011, 4, 17));
        series2.setGenre("Action, Adventure, Drama");
        series2.setSeasons(8);
        series2.setEpisodes(73);
        series2.setStatus("Completed");
        series2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BYTRiNDQwYzAtMzVlZS00NTI5LWJjYjUtMzkwNTUzMWMxZTllXkEyXkFqcGdeQXVyNDIzMzcyNzA@._V1_.jpg");
        seriesRepository.save(series2);

        Series series3 = new Series();
        series3.setTitle("Stranger Things");
        series3.setDescription("1980'lerde, küçük bir kasabada gizemli güçler ve gizli devlet deneyleri ortaya çıktığında bir grup çocuk kayıp arkadaşlarını arar.");
        series3.setReleaseDate(LocalDate.of(2016, 7, 15));
        series3.setGenre("Drama, Fantasy, Horror");
        series3.setSeasons(4);
        series3.setEpisodes(34);
        series3.setStatus("Completed");
        series3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BN2ZmYjg1YmItNWQ4OC00YWM0LWE0ZDktYThjOTZiZjhhN2Q2XkEyXkFqcGdeQXVyNjgxNTQ3Mjk@._V1_.jpg");
        seriesRepository.save(series3);

        Series series4 = new Series();
        series4.setTitle("The Office");
        series4.setDescription("Scranton, Pennsylvania'daki kağıt şirketinde çalışanların günlük yaşamlarının mockumentary tarzında komik anlatımı.");
        series4.setReleaseDate(LocalDate.of(2005, 3, 24));
        series4.setGenre("Comedy");
        series4.setSeasons(9);
        series4.setEpisodes(201);
        series4.setStatus("Completed");
        series4.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMDNkOTE4NDQtMTNmYi00MWE0LWE4ZTktYTc0NzhhNWIzNzJiXkEyXkFqcGdeQXVyMzQ2MDI5NjU@._V1_.jpg");
        seriesRepository.save(series4);

        Series series5 = new Series();
        series5.setTitle("The Mandalorian");
        series5.setDescription("Galactic Empire'ın düşüşünden yıllar sonra, bir Mandalorian ödül avcısı, galaksinin dış bölgelerinde, New Republic'in otoritesinden uzaktan faaliyet gösterir.");
        series5.setReleaseDate(LocalDate.of(2019, 11, 12));
        series5.setGenre("Action, Adventure, Fantasy");
        series5.setSeasons(3);
        series5.setEpisodes(24);
        series5.setStatus("Ongoing");
        series5.setPosterUrl("https://m.media-amazon.com/images/M/MV5BN2M5YWFjN2YtYzU2YS00NzBlLWI0NTctM2ExMWNmNWUzNTJhXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg");
        seriesRepository.save(series5);

        // Türk Dizileri
        Series turkishSeries1 = new Series();
        turkishSeries1.setTitle("Diriliş: Ertuğrul");
        turkishSeries1.setDescription("13. yüzyılda yaşayan Ertuğrul Gazi'nin hikayesi ve Osmanlı İmparatorluğu'nun kuruluş dönemindeki mücadeleler.");
        turkishSeries1.setReleaseDate(LocalDate.of(2014, 12, 10));
        turkishSeries1.setGenre("Action, Drama, History");
        turkishSeries1.setSeasons(5);
        turkishSeries1.setEpisodes(150);
        turkishSeries1.setStatus("Completed");
        turkishSeries1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BZjRlYjU3NmEtYWI2ZC00MjBlLWExZTEtYzJlYTI1MzUyZDJiXkEyXkFqcGdeQXVyMTA3MDk2NDg2._V1_.jpg");
        seriesRepository.save(turkishSeries1);

        Series turkishSeries2 = new Series();
        turkishSeries2.setTitle("Kuruluş: Osman");
        turkishSeries2.setDescription("Osman Bey'in Osmanlı İmparatorluğu'nu kurma mücadelesini anlatan tarihi dizi.");
        turkishSeries2.setReleaseDate(LocalDate.of(2019, 11, 20));
        turkishSeries2.setGenre("Action, Drama, History");
        turkishSeries2.setSeasons(5);
        turkishSeries2.setEpisodes(150);
        turkishSeries2.setStatus("Ongoing");
        turkishSeries2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BYjg1YjBmYzMtNDUxMS00MDUzLWE0ZTUtM2I5NzBiMTI2N2I4XkEyXkFqcGdeQXVyMTEwMTQ4MDE0._V1_.jpg");
        seriesRepository.save(turkishSeries2);

        Series turkishSeries3 = new Series();
        turkishSeries3.setTitle("Çukur");
        turkishSeries3.setDescription("İstanbul'un Balat semtindeki Koçovalı ailesinin çete savaşları ve aile dramlarını konu alan dizi.");
        turkishSeries3.setReleaseDate(LocalDate.of(2017, 10, 23));
        turkishSeries3.setGenre("Crime, Drama, Action");
        turkishSeries3.setSeasons(4);
        turkishSeries3.setEpisodes(126);
        turkishSeries3.setStatus("Completed");
        turkishSeries3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BYjAzZTdmNTEtYWJjNy00YmM5LWI5NzItOWE5M2FhMDY4OGQ0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg");
        seriesRepository.save(turkishSeries3);

        Series modernSeries = new Series();
        modernSeries.setTitle("The Crown");
        modernSeries.setDescription("İngiltere Kraliçesi II. Elizabeth'in hayatının ve saltanatının farklı dönemlerini anlatan biyografik dizi.");
        modernSeries.setReleaseDate(LocalDate.of(2016, 11, 4));
        modernSeries.setGenre("Biography, Drama, History");
        modernSeries.setSeasons(6);
        modernSeries.setEpisodes(60);
        modernSeries.setStatus("Completed");
        modernSeries.setPosterUrl("https://m.media-amazon.com/images/M/MV5BZjZkNzEzMTYtYWJmMi00NWE2LWJmM2EtZjFmZDkxOTNmOThmXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg");
        seriesRepository.save(modernSeries);

        Series comedySeries = new Series();
        comedySeries.setTitle("Friends");
        comedySeries.setDescription("New York'ta yaşayan altı arkadaşın günlük hayatları, ilişkileri ve komik durumlarını konu alan efsane komedi dizisi.");
        comedySeries.setReleaseDate(LocalDate.of(1994, 9, 22));
        comedySeries.setGenre("Comedy, Romance");
        comedySeries.setSeasons(10);
        comedySeries.setEpisodes(236);
        comedySeries.setStatus("Completed");
        comedySeries.setPosterUrl("https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg");
        seriesRepository.save(comedySeries);

        System.out.println("✅ " + seriesRepository.count() + " dizi yüklendi!");
    }
}