package com.aline.openApi;

import com.aline.openApi.models.ProdutoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenApiApplicationTests {

    @Test
    void contextLoads() {
    }

    //https://www.baeldung.com/java-optional
    //https://medium.com/@racc.costa/optional-no-java-8-e-no-java-9-7c52c4b797f1
    /*
    A principal proposta deste recurso é encapsular o retorno de métodos e informar se um valor do tipo <T> está presente ou ausente.
     */
    @Test
    public void whenCreatesEmptyOptional_thenCorrect() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
        String nome = "teste is presente";
        Optional<String> optional = Optional.of(nome);
        assertTrue(optional.isPresent());
    }


    @Test
    public void givenNonNull_whenCreatesNullable_thenCorrect() {
        String nome = "valor nulo";
        Optional<String> optional = Optional.ofNullable(nome);
        assertTrue(optional.isPresent());
    }

    @Test
    public void givenNull_whenCreatesNullable_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
        assertFalse(opt.isPresent());
    }

    @Test
    public void givenOptional_whenIsPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("Baeldung");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    //java 11
    @Test
    public void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
        Optional<String> opt = Optional.of("string vazia?");
        assertFalse(opt.isEmpty());

        opt = Optional.ofNullable(null);
        assertTrue(opt.isEmpty());
    }

    @Test
    public void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> optional = Optional.of("if present");
        optional.ifPresent(s -> System.out.println(s.length()));
    }

    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullNome = null;
        String nome = Optional.ofNullable(nullNome).orElse("Aline");
        assertEquals("Aline", nome);
    }

    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
        assertEquals("john", name);
    }


    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;

        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        System.out.println("Using orElseGet:");
        String defaultText
                = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse("asdfs");
        assertEquals("Text present", defaultText);
    }

    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }

    @Test
    public void whenOrElseThrowWorks_thenCorrect() {
        String nullName = null;

        assertThrows(IllegalArgumentException.class, () ->  Optional.ofNullable(nullName).orElseThrow(
                IllegalArgumentException::new));
    }

    //java 10
    @Test
    public void whenNoArgOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        assertThrows(NoSuchElementException.class, () -> Optional.ofNullable(nullName).orElseThrow());
    }

    @Test
    public void whenIdIsNonNull_thenNoExceptionIsThrown(){
        String nome = "akube";
        assertAll(() -> Optional.ofNullable(nome).orElseThrow(RuntimeException::new));
    }

    @Test
    public void givenOptional_whenGetsValue_thenCorrect() {
        Optional<String> opt = Optional.of("openapi");
        String name = opt.get();
        assertEquals("openapi", name);
    }

    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer ano = 2016;
        Optional<Integer> anoOptional = Optional.of(ano);
        boolean ano2016 = anoOptional.filter(a -> a == 2016).isPresent();
        assertTrue(ano2016);

        boolean ano2017 = anoOptional.filter(a -> a == 2017).isPresent();
        assertFalse(ano2017);
    }

    @Test
    public void whenFiltersWithoutOptional_thenCorrect() {
        assertTrue(new ProdutoEntity("modem", BigDecimal.TEN).valorNaMedia());
        assertFalse(new ProdutoEntity("modem", BigDecimal.valueOf(9.9)).valorNaMedia());
        assertFalse(new ProdutoEntity("modem", null).valorNaMedia());
        assertFalse(new ProdutoEntity("modem", BigDecimal.valueOf(15)).valorNaMedia());
    }

    @Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertTrue(new ProdutoEntity("modem", BigDecimal.TEN).valorNaMediaOptional());
        assertFalse(new ProdutoEntity("modem", BigDecimal.valueOf(9.9)).valorNaMediaOptional());
        assertFalse(new ProdutoEntity("modem", null).valorNaMediaOptional());
        assertFalse(new ProdutoEntity("modem", BigDecimal.valueOf(15)).valorNaMediaOptional());
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList(
                "paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listaCompanias = Optional.of(companyNames);

        boolean tamanho = listaCompanias.map(List::isEmpty).orElse(false);
        assertFalse(tamanho);
    }

    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> optional = Optional.of(password);
        boolean senhaCorreta = optional.filter(s -> s.equals("password"))
                .isPresent();
        assertFalse(senhaCorreta);

        //
        senhaCorreta = optional.map(String::trim).filter(s -> s.equals("password"))
                .isPresent();
        assertTrue(senhaCorreta);
    }

    @Test
    public void givenOptional_whenFlatMapWorks_thenCorrect2() {
        ProdutoEntity person = new ProdutoEntity("john", BigDecimal.valueOf(26));
        Optional<ProdutoEntity> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper
                = personOptional.map(ProdutoEntity::getOptionalName);
        Optional<String> nameOptional
                = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertEquals("john", name1);

        //flatmap
        //The difference is that map transforms values only when they are unwrapped whereas flatMap takes a wrapped value and unwraps it before transforming it.
        String name = personOptional
                .flatMap(ProdutoEntity::getOptionalName)
                .orElse("");
        assertEquals("john", name);
    }

    //wrong
    public static List<ProdutoEntity> search(List<ProdutoEntity> people, String name, Optional<Integer> age) {
        // Null checks for people and name
        return people.stream()
                .filter(p -> p.getOptionalName().equals(name))
              //  .filter(p -> p.getValor().get() >= age.orElse(0))
                .collect(Collectors.toList());
    }


}
