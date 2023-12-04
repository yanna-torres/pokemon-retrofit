<h1>Pokédex 1ª Geração <img src="https://seeklogo.com//images/P/pokeball-logo-DC23868CA1-seeklogo.com.png" width="35px">
</h1>

> **Aluna:** Yanna Torres
>
> **Matrícula:** 507773
>
> **Disciplina:** Programação para Dispositivos Móveis (SMD0122)
>
> **Professor:** Dr. Windson Viana

**Sumário: [A Atividade](#a-atividade-page_with_curl) • [A API](#a-api-woman_technologist) • [Main Activity](#main-activity-pushpin) • [Resultados](#resultados-play_or_pause_button) • [Próximos Passos](#próximos-passos-spiral_notepad) •**

## A atividade :page_with_curl:

Essa atividade é um complemento do SEM02, afim de demonstrar a aplicação dos conceitos apresentados.

Neste caso, esse aplicativo demonstra uma POC que consome serviços REST usando a biblioteca [Retrofit](https://square.github.io/retrofit/).

## A API :woman_technologist:

Para fazer a demonstração, decidi usar a API própria do Pokémon: a [PokéAPI](https://pokeapi.co/).

Assim, se utilizou os seguintes códigos e endpoints para fazer as requisições:

### Lista completa de Pokémons

```kotlin
interface PokemonService {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsApiResult>

    // Rest of the code
}
```

```kotlin
object PokemonRepository {
    // https://pokeapi.co/api/v2/pokemon/?limit=151
    private val service: PokemonService;

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        service = retrofit.create(PokemonService::class.java)
    }

    // Chama o método e define o limite para 151 -> pokémons da 1ª geração
    fun listPokemons(limit: Int = 151): PokemonsApiResult? {
        val call = service.listPokemons(limit);
        return call.execute().body()
    }

    // Rest of the code
}
```

```kotlin
data class PokemonsApiResult (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult (
    val name: String,
    val url: String,
)
```

### Informações específicas de um Pokémon

```kotlin
interface PokemonService {
    // Rest of the code

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number")number: Int): Call<PokemonApiResult>
}
```

```kotlin
object PokemonRepository {
    // https://pokeapi.co/api/v2/pokemon/?limit=151
    private val service: PokemonService;

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        service = retrofit.create(PokemonService::class.java)
    }

    // Rest of the code

    fun getPokemon(number: Int):PokemonApiResult? {
        val call = service.getPokemon(number)
        return call.execute().body()
    }
}
```

```kotlin
data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)
```

## Main Activity :pushpin:

A seguir está o código referente a `MainActivity.ky`. Na função `loadPokemons()` chama-se inicialmente o método `listPokemons()` que chama todos os 151 Pokémons da primeria geração.

Após isso, ocorre uma iteração e chama-se a a função `getPokemon(number)` para cada item, carregando as informações de cada Pokémon e depois se mapeia o resultado desta requisição para a classe modelo `Pokémon`.

Por fim, chama-se o método `loadRecyclerView()` que chama o `PokemonAdapter(pokemons)` que configura o RecyclerView do layout.

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var pokemons = emptyList<Pokemon?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.rvPokemon)

        Thread(Runnable {
            loadPokemons()
        }).start();
    }

    private fun loadPokemons() {
        val pokemonsApiResult = PokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let { it ->
            pokemons = it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )
                }
            }

            recyclerView.post {
                loadRecyclerView()
            }
        }
    }

    private fun loadRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)

    }
}
```

## Resultados :play_or_pause_button:



## Próximos Passos :spiral_notepad:

Visto que este aplicativo é somente uma POC, pretendo desenvolver melhorias e completar a Pokedéx, deixando-a funcional.

Dessa forma, pretendo:

1. Melhorar o layout da tela incial;
2. Permitir que o usuário acesse informações _específicas_ de cada pokémon;
3. Permitir que o usuário se cadastre e possua uma pokédex própria.

---
